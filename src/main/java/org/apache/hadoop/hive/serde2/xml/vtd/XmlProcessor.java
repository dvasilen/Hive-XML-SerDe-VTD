/**
 * Copyright (C) 2013 by Dmitry Vasilenko. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.apache.hadoop.hive.serde2.xml.vtd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.spss.hive.serde2.xml.processor.AbstractXmlProcessor;
import com.ibm.spss.hive.serde2.xml.processor.XmlNodeArray;
import com.ibm.spss.hive.serde2.xml.processor.XmlProcessorContext;
import com.ibm.spss.hive.serde2.xml.processor.XmlQuery;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;

/**
 * VTD XML processor
 */
public class XmlProcessor extends AbstractXmlProcessor {

    private List<VtdXmlQuery> queries = new ArrayList<VtdXmlQuery>();

    /**
     * @see com.ibm.spss.hive.serde2.xml.processor.XmlProcessor#initialize(com.ibm.spss.hive.serde2.xml.processor.XmlProcessorContext)
     */
    @Override
    public void initialize(XmlProcessorContext xmlProcessorContext) {
        super.initialize(xmlProcessorContext);
        try {
            for (XmlQuery xmlQuery : xmlProcessorContext.getXmlQueries()) {
                this.queries.add(new VtdXmlQuery(xmlQuery));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see com.ibm.spss.hive.serde2.xml.processor.XmlProcessor#parse(java.lang.String)
     */
    @Override
    public Map<String, ?> parse(String value) {
        Map<String, XmlNodeArray> result = new HashMap<String, XmlNodeArray>();
        try {
            byte[] document = value.getBytes();
            VTDGen generator = new VTDGen();
            generator.setDoc(document);
            generator.parse(true);
            VTDNav navigator = generator.getNav();
            for (VtdXmlQuery query : this.queries) {
                VtdPilot pilot = query.getPilot();
                pilot.resetXPath();
                pilot.bind(navigator);
                result.put(query.getName(), parse(pilot).withName(query.getName()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Given the VTD XML pilot returns the XML array
     * 
     * @param pilot
     *            the VTD XML pilot
     */
    private XmlNodeArray parse(VtdPilot pilot) throws Exception {
        XmlNodeArray nodeArray = new XmlNodeArray();
        while (pilot.evalXPath() != -1) {
            VtdNode vtdNode = new VtdNode(pilot);
            if (vtdNode.isValid()) {
                nodeArray.add(vtdNode);
            }
        }
        return nodeArray;
    }
}

/**
* Hive-XML-SerDe-VTD: VTD Processor for Apache Hive XML SerDe 
*
* Copyright (C) 2013 Dmitry Vasilenko
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.ximpleware.hive.serde2.xml.vtd;

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

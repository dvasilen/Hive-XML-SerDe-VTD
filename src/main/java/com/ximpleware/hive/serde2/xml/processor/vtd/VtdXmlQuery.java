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
package com.ximpleware.hive.serde2.xml.processor.vtd;

import com.ibm.spss.hive.serde2.xml.processor.XmlQuery;
import com.ximpleware.XPathParseException;

/**
 * Creates a VTD XML query
 */
class VtdXmlQuery extends XmlQuery {

    private VtdPilot pilot = null;

    /**
     * Conversion constructor
     * 
     * @param xmlQuery
     */
    VtdXmlQuery(XmlQuery xmlQuery) {
        super(xmlQuery.getQuery(), xmlQuery.getName());
        this.pilot = new VtdPilot();
        this.pilot.enableCaching(true);
        try {
            this.pilot.selectXPath(xmlQuery.getQuery());
        } catch (XPathParseException e) {
            throw new RuntimeException(getQuery(), e);
        }
    }

    /**
     * Returns the associated VTD pilot
     * 
     * @return the associated VTD pilot
     */
    VtdPilot getPilot() {
        return this.pilot;
    }
}

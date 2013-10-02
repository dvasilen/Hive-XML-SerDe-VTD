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

import com.ximpleware.AutoPilot;
import com.ximpleware.VTDNav;

/**
 * VTD XML pilot
 */
public class VtdPilot extends AutoPilot {

    /**
     * Conversion constructor
     * 
     * @param inVTDNav
     */
    public VtdPilot(VTDNav inVTDNav) {
        super(inVTDNav);
    }

    /**
     * Creates VTD XML pilot
     */
    public VtdPilot() {
    }

    /**
     * Returns VTD XML navigator
     * 
     * @return
     */
    public VTDNav getVTDNav() {
        return this.vn;
    }

}

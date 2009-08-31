package org.yestech.episodic.util;

import static org.yestech.episodic.util.EpisodicUtil.join;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author A.J. Wright
 */
public class CommaSeperatedStringArrayXmlConverter extends XmlAdapter<String, String[]> {
    @Override
    public String[] unmarshal(String v) throws Exception {
        if (v != null && !"".equals(v)) {
            if (v.contains(",")) {
                return v.split(",");
            }
            else {
                return new String[] {v};
            }
        }
        return new String[0];
    }

    @Override
    public String marshal(String[] v) throws Exception {
        return join(v);
    }
}

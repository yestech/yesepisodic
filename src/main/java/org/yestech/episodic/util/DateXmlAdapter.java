package org.yestech.episodic.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author A.J. Wright
 */
@XmlTransient
public class DateXmlAdapter extends XmlAdapter<String, DateTime> {

    private static final DateTimeFormatter FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public DateTime unmarshal(String v) throws Exception {
        return FORMAT.parseDateTime(v);
    }

    @Override
    public String marshal(DateTime v) throws Exception {
        return v.toString(FORMAT);
    }

}

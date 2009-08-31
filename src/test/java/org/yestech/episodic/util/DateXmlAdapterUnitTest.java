package org.yestech.episodic.util;

import org.joda.time.DateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author A.J. Wright
 */
public class DateXmlAdapterUnitTest {

    @Test
    public void testUnmarshall() throws Exception {
        String date = "2009-09-12 12:13:12";
        DateXmlAdapter xmlAdapter = new DateXmlAdapter();
        DateTime result = xmlAdapter.unmarshal(date);

        assertEquals(2009, result.getYear());
        assertEquals(9, result.getMonthOfYear());
    }

    public void testMarshall() throws Exception {

        DateTime date = new DateTime(2009, 9, 12, 12, 13, 12, 0);
        DateXmlAdapter xmlAdapter = new DateXmlAdapter();
        String result = xmlAdapter.marshal(date);
        assertEquals("2009-09-12 12:13:12", result);
    }


}

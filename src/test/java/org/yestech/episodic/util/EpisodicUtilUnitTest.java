package org.yestech.episodic.util;

import static junit.framework.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.yestech.episodic.objectmodel.*;
import static org.yestech.episodic.util.EpisodicUtil.*;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;

/**
 * @author A.J. Wright
 */
public class EpisodicUtilUnitTest {

    @Test
    public void testJoin() {
        String[] array = new String[]{"foo", "bar", "baz"};
        String result = join(array);
        assertEquals("foo,bar,baz", result);
    }

    @Test
    public void testSortedKeys() {
        Map<String, String> map = new HashMap<String, String>(3);
        map.put("beta", "slkdfjfdjlk");
        map.put("gamma", "sjldkfjklsdf");
        map.put("alpha", "sdlkafjdfs");

        SortedSet<String> strings = sortedKeys(map);
        assertNotNull(strings);
        assertEquals(3, strings.size());
        Iterator<String> it = strings.iterator();
        assertEquals("alpha", it.next());
        assertEquals("beta", it.next());
        assertEquals("gamma", it.next());
    }

    @Test
    public void testExpires() {
        long now = new DateTime(DateTimeZone.UTC).getMillis();
        String s = EpisodicUtil.expires();
        long value = Long.parseLong(s) * 1000L;
        assertTrue("The expires values show be larger then the current milliseconds", value > now);
    }


}

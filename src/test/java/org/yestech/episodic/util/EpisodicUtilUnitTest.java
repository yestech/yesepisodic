package org.yestech.episodic.util;

import static junit.framework.Assert.*;
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
    public void testUnmarshallShows() throws JAXBException, UnsupportedEncodingException {
        Object o = unmarshall(new ByteArrayInputStream(ShowsUnitTest.XML.getBytes("utf-8")));
        assertNotNull(o);
        assertTrue(o instanceof Shows);
    }

    @Test
    public void testCreateAssertResponse() throws JAXBException, UnsupportedEncodingException {
        Object o = unmarshall(new ByteArrayInputStream(CreateAssetResponseUnitTest.XML.getBytes("utf-8")));
        assertNotNull(o);
        assertTrue(o instanceof CreateAssetResponse);
    }

    @Test
    public void testCreateEpisodeResponse() throws JAXBException, UnsupportedEncodingException {
        Object o = unmarshall(new ByteArrayInputStream(CreateEpisodeResponseUnitTest.XML.getBytes("utf-8")));
        assertNotNull(o);
        assertTrue(o instanceof CreateEpisodeResponse);
    }

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


}

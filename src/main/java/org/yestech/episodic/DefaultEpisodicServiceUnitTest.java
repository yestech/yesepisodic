package org.yestech.episodic;

import org.yestech.episodic.objectmodel.*;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.Iterator;

import static junit.framework.Assert.*;

/**
 * @author A.J. Wright
 */
public class DefaultEpisodicServiceUnitTest {

    @Test
    public void testUnmarshallShows() throws JAXBException, UnsupportedEncodingException {
        DefaultEpisodicService svc = new DefaultEpisodicService(null, null);
        Object o = svc.unmarshall(new ByteArrayInputStream(ShowsUnitTest.XML.getBytes("utf-8")));
        assertNotNull(o);
        assertTrue(o instanceof Shows);
    }

    @Test
    public void testCreateAssertResponse() throws JAXBException, UnsupportedEncodingException {
        DefaultEpisodicService svc = new DefaultEpisodicService(null, null);
        Object o = svc.unmarshall(new ByteArrayInputStream(CreateAssetResponseUnitTest.XML.getBytes("utf-8")));
        assertNotNull(o);
        assertTrue(o instanceof CreateAssetResponse);
    }

    @Test
    public void testCreateEpisodeResponse() throws JAXBException, UnsupportedEncodingException {
        DefaultEpisodicService svc = new DefaultEpisodicService(null, null);
        Object o = svc.unmarshall(new ByteArrayInputStream(CreateEpisodeResponseUnitTest.XML.getBytes("utf-8")));
        assertNotNull(o);
        assertTrue(o instanceof CreateEpisodeResponse);
    }

    @Test
    public void testJoin() {
        String[] array = new String[] { "foo", "bar", "baz"};
        DefaultEpisodicService svc = new DefaultEpisodicService(null, null);
        String result = svc.join(array);
        assertEquals("foo,bar,baz", result);
    }

    @Test
    public void testSortedKeys() {
        Map<String,String> map = new HashMap<String, String>(3);
        map.put("beta", "slkdfjfdjlk");
        map.put("gamma", "sjldkfjklsdf");
        map.put("alpha", "sdlkafjdfs");

        DefaultEpisodicService svc = new DefaultEpisodicService(null, null);

        SortedSet<String> strings = svc.sortedKeys(map);
        assertNotNull(strings);
        assertEquals(3, strings.size());
        Iterator<String> it = strings.iterator();
        assertEquals("alpha", it.next());
        assertEquals("beta", it.next());
        assertEquals("gamma", it.next());
    }

    @Test
    public void testBuildSignatureString() {
        String test = "77c062e551279b0a0b8bc69f9709f33bexpires=1229046347show_id=13";
        String secret = "77c062e551279b0a0b8bc69f9709f33b";
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("show_id", "13");
        map.put("expires", "1229046347");

        DefaultEpisodicService svc = new DefaultEpisodicService(null, null);
        String result = svc.buildSignatureString(secret, map);
        assertEquals(test, result);
    }


}

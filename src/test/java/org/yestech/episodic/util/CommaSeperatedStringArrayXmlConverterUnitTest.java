package org.yestech.episodic.util;

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * @author A.J. Wright
 */
public class CommaSeperatedStringArrayXmlConverterUnitTest {
    @Test
    public void testUnmarshal() throws Exception {
        CommaSeperatedStringArrayXmlConverter converter = new CommaSeperatedStringArrayXmlConverter();
        String[] strings = converter.unmarshal("foo,bar");
        assertNotNull(strings);
        assertEquals(2, strings.length);
        assertEquals("foo", strings[0]);
        assertEquals("bar", strings[1]);

        strings = converter.unmarshal("baz");
        assertNotNull(strings);
        assertEquals(1, strings.length);
        assertEquals("baz", strings[0]);

        strings = converter.unmarshal(null);
        assertNotNull(strings);
        assertEquals(0, strings.length);
    }

    @Test
    public void testMarshal() throws Exception {
        CommaSeperatedStringArrayXmlConverter converter = new CommaSeperatedStringArrayXmlConverter();
        String result = converter.marshal(new String[]{"foo", "bar"});
        assertNotNull(result);
        assertEquals("foo,bar", result);
    }
}

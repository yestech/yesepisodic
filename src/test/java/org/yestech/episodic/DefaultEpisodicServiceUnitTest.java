package org.yestech.episodic;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author A.J. Wright
 */
public class DefaultEpisodicServiceUnitTest {

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

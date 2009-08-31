package org.yestech.episodic;

import org.junit.Test;
import org.junit.Before;
import org.yestech.episodic.objectmodel.Shows;
import static junit.framework.Assert.*;

/**
 * @author A.J. Wright
 */
public class EpisodicServiceManualTest {

    private EpisodicService episodicService;

    @Before
    public void buildService() {
        String secret = System.getProperty("test.secret");
        assertNotNull(secret);

        String apiKey = System.getProperty("test.apiKey");
        assertNotNull(apiKey);

        episodicService = new DefaultEpisodicService(secret, apiKey);
    }


    @Test
    public void testShows() {
        Shows shows = episodicService.getShows(null, null, null, null, null);
        System.out.println(shows);
    }


}

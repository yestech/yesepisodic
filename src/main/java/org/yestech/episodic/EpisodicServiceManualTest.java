package org.yestech.episodic;

import org.junit.Test;
import org.junit.Before;
import org.yestech.episodic.objectmodel.Shows;
import static junit.framework.Assert.*;

import java.io.File;

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

        episodicService = new DefaultEpisodicService(secret, apiKey, "localhost", 8888);
    }


    @Test
    public void testShows() {
        Shows shows = episodicService.getShows(null, null, null, null, null);
        System.out.println(shows);
    }

    @Test
    public void testShows2() {
        Shows shows = episodicService.getShows(new String[] { "0" }, null, null, null, null);
        System.out.println(shows);
    }

    @Test
    public void testCreateAsset() {

        String show = episodicService.getShows(null, null, null, null, null).getShow().get(0).getId();


        String path = System.getProperty("test.movie.path");
        assertNotNull(path);

        File file = new File(path);
        assertTrue(file.exists());
        
        String l = episodicService.createAsset(show, "test", file, "test");
        assertNotNull(l);
        System.out.println(l);

        String id = episodicService.createEpisode(show, "episode2", new String[] {l}, true, "tes test test", null, "test");
        assertNotNull(id);
        System.out.println(id);

    }



}

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

        episodicService = new DefaultEpisodicService(secret, apiKey);
    }


    @Test
    public void testShows() {
        Shows shows = episodicService.getShows(null, null, null, null, null);
        System.out.println(shows);
    }

    @Test
    public void testCreateAsset() {
        long show = 0L;


        String path = System.getProperty("test.movie.path");
        assertNotNull(path);

        File file = new File(path);
        assertTrue(file.exists());
        
        long l = episodicService.createAsset(show, "test", file, "test");
        assertTrue(l > -1);
        System.out.println(l);

        String id = episodicService.createEpisode(show, "my test asset", new long[]{l}, true, "tes test test", null, "test");
        assertNotNull(id);
        System.out.println(id);

    }

    @Test
    public void testCreateEpisode() {
        long asset = 8963;

        String id = episodicService.createEpisode(0, "my test asset", new long[]{asset}, true, "tes test test", null, "test");
        assertNotNull(id);
        System.out.println(id);
    }


}

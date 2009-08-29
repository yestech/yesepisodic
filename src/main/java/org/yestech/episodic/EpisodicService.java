package org.yestech.episodic;

import org.yestech.episodic.objectmodel.Shows;

import java.io.File;
import java.io.IOException;

/**
 * Provides a java api for accessing Episodic's Platform API:
 * http://app.episodic.com/help/server_api
 *
 * @author A.J. Wright
 */
public interface EpisodicService {

    /**
     * Possible fieds to sort by
     */
    public static enum SortBy {
        updated_at,
        created_at,
        name
    }

    /**
     * Possible sort directions
     */
    public static enum SortDir {
        asc, dsc
    }

    /**
     * The Shows method can be used to get data about a specific show, set of shows or all shows in your network.
     *
     * @param sortBy  (optional) The sort_by parameter is optional and specifies a field to sort the results by.
     * @param sortDir (optional) The sort_dir parameter is optional and specifies the sort direction.
     * @param page    (optional) A value that must be an integer indicating the page number to return the results for.
     *                The default is 1.
     * @param perPage  
     * @param showIds (optional) ids of show's to return
     * @return
     * @throws java.io.IOException
     */
    Shows getShows(SortBy sortBy, SortDir sortDir, Integer page, Integer perPage, String... showIds) throws IOException;


    long createAsset(String showId, String name, File file, String... tags) throws IOException;

    long createEpisode(String showId, String name, String[] assetIds, boolean publish, String description,
                       String pingUrl, String... tags) throws IOException;
}

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
     * @param showIds (optional) ids of show's to return
     * @param sortBy  (optional) The sort_by parameter is optional and specifies a field to sort the results by.
     * @param sortDir (optional) The sort_dir parameter is optional and specifies the sort direction.
     * @param page    (optional) A value that must be an integer indicating the page number to return the results for.
     *                The default is 1.
     * @param perPage (optional) A value that must be an integer indicating the number of items per page.
     *                The default is 20. NOTE: The smaller this value is the better your response times will be.
     * @return All the shows that match the specified params.
     */
    Shows getShows(String[] showIds, SortBy sortBy, SortDir sortDir, Integer page, Integer perPage);

    /**
     * The createAsset method is used to upload a new video or image asset for use in one of your shows.
     *
     * @param showId (required) The id of the show to which the newly created asset will belong.
     * @param name   (required) The name of the new asset. This value must be less than 255 characters.
     * @param file   (required) The video or image asset data. This file must be less than 500MB.
     * @param tags   (optional) Tags to be applied to the new asset.
     * @return The id of the newly created asset.
     */
    long createAsset(long showId, String name, File file, String... tags);

    /**
     * Creates a new episode.
     *
     * @param showId      (required) The id of the show to which the newly created episode will belong.
     * @param name        (required) The name of the new episode. Episode names must be unique and less than 255 characters.
     * @param assetIds    (required) A list of comma separated valid asset ids in the order they should appear in the episode.
     * @param publish     (required) Either 'true' or 'false'. A value of 'true' indicates that the episode should
     *                    be submitted for publishing immediately following its creation. A value of 'false'
     *                    indicates that the episode should be created but not published.
     *                    Unpublished episodes may be published manually using the Episodic web application.
     * @param description (optional) A string value to be used as the description for the episode.
     *                    Descriptions must be less than 255 characters.
     * @param pingUrl     (optional) This URL is used by Episodic to notify you when publishing completes.
     *                    Episodic will issue a GET request against this url after appending the parameters episode_id
     *                    and status. The value of the status parameter will be either 'done', 'cancelled', or 'failed'.
     *                    This value is ignored unless the publish parameter is set to 'true'.
     * @param tags        (optional) Tags to be applied to the new episode.
     * @return The id of the new episode.
     */
    String createEpisode(String showId, String name, String[] assetIds, boolean publish, String description,
                       String pingUrl, String... tags);
}

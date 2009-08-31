package org.yestech.episodic;

import org.yestech.episodic.objectmodel.Shows;
import org.yestech.episodic.objectmodel.Episodes;
import org.yestech.episodic.objectmodel.Episode;

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

    public static enum SearchType {
        tags, name_description, all
    }

    public static enum TagMode {
        any, all
    }

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
     * The Episodes method can be used to get data about a specific episode, set of episodes or all episodes in a show.
     * This method also allows the for searching for episodes using a search term and/or tag(s).
     *
     * @param showIds      (optional) Shows to query against.
     *                     If this param is not provided then all shows in your network are queried.
     *                     To get a list of shows see Shows.
     * @param episodeIds   (optional) The id parameter is optional and can contain either a single integer id or a
     *                     list of integer ids.
     * @param searchTerm   (optional) The search_term parameter is a string and/or tags and allow to specify what to search.
     *                     If the search term is multiple tags the tags should be separated by commas.
     * @param searchType   (optional) The search_type parameter must be one of "tags", "name_description" or "all". The default is "all".
     * @param tagMode      (optional)The tag_mode parameter can be "any" for an OR combination of tags, or "all" for an
     *                     AND combination. The default is "any". This parameter is ignored if the search_type is "name_description".
     * @param status       (optional) The status parameter can be used to limit the list of episodes with a certain
     *                     publishing status. The value must be a comma delimited list of one or more of "off_the_air",
     *                     "publishing", "on_the_air", "waiting_to_air", "publish_failed"
     * @param sortBy       (optional) The sort_by parameter is optional and specifies a field to sort the results by.
     *                     The value must be one of "updated_at", "created_at", "air_date" or "name". The default is "created_at".
     * @param sortDir      (optional) The sort_dir parameter is optional and specifies the sort direction. The value must be one of "asc" or "desc". The default is "asc".
     * @param includeViews (optional) A value that must be one of "true" or "false" to indicate if total views and
     *                     complete views should be included in the response. The default is "false".
     *                     NOTE: Setting this to "true" may result in slower response times.
     * @param page         (optional) A value that must be an integer indicating the page number to return the results for. The default is 1.
     * @param perPage      (optional) A value that must be an integer indicating the number of items per page.
     *                     The default is 20. NOTE: The smaller this value is the better your response times will be.
     * @param embedWidth   (optional) An integer value in pixels that specifies the width of the video.
     *                     The returned embed code width may be larger that this to account for player controls
     *                     depending on the player you are using. If only the width is provided, the height is determined
     *                     by maintaining the aspect ratio.
     * @param embedHeight  (optional) An integer value in pixels that specifies the height of the player.
     *                     The embed code height may be larger that this to account for player controls depending on the
     *                     player you are using. The default height is 360.
     * @return Episodes that match the parameters.
     */
    Episodes getEpisodes(String[] showIds,
                         String[] episodeIds,
                         String searchTerm,
                         SearchType searchType,
                         TagMode tagMode,
                         Episode.EpisodeStatus status,
                         SortBy sortBy,
                         SortDir sortDir,
                         Boolean includeViews,
                         Integer page,
                         Integer perPage,
                         Integer embedWidth,
                         Integer embedHeight);
}

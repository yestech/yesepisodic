package org.yestech.episodic;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yestech.episodic.objectmodel.*;
import org.yestech.episodic.util.EpisodicMultipartEntity;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.String.valueOf;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.yestech.episodic.util.EpisodicUtil.*;


/**
 * Default implementation of Episodic service.
 * <p/>
 * Requests are made using apache commons http client. The responses are handled with jaxb.
 *
 * @author A.J. Wright
 */
public class DefaultEpisodicService implements EpisodicService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultEpisodicService.class);


    protected static final String SCHEME = "http";
    protected static final String HOST = "api.episodic.com";
    protected static final int PORT = 80;
    protected static final String WRITE_API_PREFIX = "/api/v2/write/";
    protected static final String QUERY_API_PREFIX = "/api/v2/query/";

    protected String apiKey;
    protected String secret;
    protected HttpClient client;

    /**
     * Creates a new instance .
     *
     * @param secret The Secret key assigned to the user
     * @param apiKey The API key assigned to the user
     */
    public DefaultEpisodicService(String secret, String apiKey) {
        this.secret = secret;
        this.apiKey = apiKey;
        client = new DefaultHttpClient();
    }

    public DefaultEpisodicService(String secret, String apiKey, String proxyHost, int proxyPort) {
        this(secret, apiKey);
        client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(proxyHost, proxyPort));
    }

    public void destroy() {
        client.getConnectionManager().shutdown();
    }

    public String createAsset(String showId, String name, File file, String... tags) {

        HttpPost method = new HttpPost(buildWriteURI("create_asset"));

        try {

            Map<String, String> map = new HashMap<String, String>();
            map.put("expires", expires());
            map.put("name", name);

            if (tags != null && tags.length > 0) {
                String tagString = join(tags);
                map.put("tags", tagString);
            }
            map.put("show_id", valueOf(showId));
            String signature = generateSignature(secret, map);
            map.put("signature", signature);
            map.put("key", apiKey);

            EpisodicMultipartEntity multipartEntity = new EpisodicMultipartEntity();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                multipartEntity.addPart(entry.getKey(), new StringBody(entry.getValue()));
            }
            multipartEntity.addPart("uploaded_data", new FileBody(file));
            method.setEntity(multipartEntity);

            HttpResponse response = client.execute(method);
            checkStatus(response);

            Object o = unmarshall(response);

            if (o instanceof ErrorResponse) {
                throw new EpisodicException((ErrorResponse) o);
            } else if (o instanceof CreateAssetResponse) {
                return ((CreateAssetResponse) o).getAssetId();
            } else {
                throw new IllegalStateException("unknown response : " + o);
            }


        } catch (Exception e) {
            logger.error(e.getMessage(),  e);
            method.abort();
            throw new TransportException(e.getMessage(), e);
        }

    }

    public String createEpisode(String showId, String name, String[] assetIds, boolean publish, String description,
                                String pingUrl, String... tags) {

        HttpPost method = new HttpPost(buildWriteURI("create_episode"));

        Map<String, String> map = new HashMap<String, String>();
        map.put("expires", expires());
        map.put("name", name);

        if (tags != null && tags.length > 0) {
            String tagString = join(tags);
            map.put("tags", tagString);
        }

        map.put("show_id", valueOf(showId));
        map.put("publish", valueOf(publish));
        if (description != null) map.put("description", description);
        if (pingUrl != null) map.put("ping_url", pingUrl);

        map.put("asset_ids", join(assetIds));
        if (pingUrl != null) map.put("ping_url", pingUrl);

        map.put("signature", generateSignature(secret, map));
        map.put("key", apiKey);

        try {

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(toNameValuePairList(map), "UTF-8");
            method.setEntity(entity);

            HttpResponse response = client.execute(method);
            checkStatus(response);

            Object o = unmarshall(response);

            if (o instanceof ErrorResponse) {
                throw new EpisodicException((ErrorResponse) o);
            } else if (o instanceof CreateEpisodeResponse) {
                return ((CreateEpisodeResponse) o).getEpisodeId();
            } else {
                throw new IllegalStateException("unknown response : " + o);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(),  e);
            method.abort();
            throw new TransportException(e.getMessage(), e);
        }

    }

    public Shows getShows(String[] showIds, SortBy sortBy, SortDir sortDir, Integer page, Integer perPage) {


        Map<String, String> map = new HashMap<String, String>();
        map.put("expires", expires());
        if (sortBy != null) map.put("sort_by", sortBy.name());
        if (sortDir != null) map.put("sort_dir", sortDir.name());
        if (page != null) map.put("page", page.toString());
        if (perPage != null) map.put("per_page", perPage.toString());
        if (showIds != null && showIds.length > 0) map.put("id", join(showIds));

        map.put("signature", generateSignature(secret, map));
        map.put("key", apiKey);

        HttpGet method = new HttpGet(buildQueryURI("shows", map));

        try {
            HttpResponse response = client.execute(method);
            checkStatus(response);

            Object o = unmarshall(response);

            if (o instanceof ErrorResponse) {
                throw new EpisodicException((ErrorResponse) o);
            } else if (o instanceof Shows) {
                return (Shows) o;
            } else {
                throw new IllegalStateException("unknown response : " + o);
            }


        } catch (Exception e) {
            logger.error(e.getMessage(),  e);
            method.abort();
            throw new TransportException(e.getMessage(), e);
        }

    }

    public Episodes getEpisodes(String[] showIds,
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
                                Integer embedHeight) {

        Map<String, String> map = buildEpisodeMap(showIds, episodeIds, searchTerm, searchType, tagMode, status, sortBy,
                sortDir, includeViews, page, perPage, embedWidth, embedHeight);

        map.put("signature", generateSignature(secret, map));
        map.put("key", apiKey);

        HttpGet method = new HttpGet(buildQueryURI("episodes", map));

        try {
            HttpResponse response = client.execute(method);
            checkStatus(response);

            Object o = unmarshall(response);

            if (o instanceof ErrorResponse) {
                throw new EpisodicException((ErrorResponse) o);
            } else if (o instanceof Episodes) {
                return (Episodes) o;
            } else {
                throw new IllegalStateException("unknown response : " + o);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(),  e);
            method.abort();
            throw new TransportException(e.getMessage(), e);
        }
    }


    /**
     * Generates a signature with the algorithm specified by episodic:
     * <p/>
     * API Signature Generation
     * <ol>
     * <li> All API requests must include a signature parameter. The signature is generated performing the following steps.
     * <li> Concatenate all query parameters except the API Key in the format "name=value" in alphabetical order by parameter name. There should not be an ampersand or any separator between "name=value" pairs.
     * <li> Append the string from step 1 to the Secret Key so that you have [Secret Key][String from step 1] (ex. 77c062e551279b0a0b8bc69f9709f33bexpires=1229046347show_id=13).
     * <li>Generate the SHA-256 hash value for the string resulting from steps 1 and 2.
     * <ol>
     *
     * @param secret The secret key provided by episodic.
     * @param map    A map of the parameters to be sent to.
     * @return The signature needed for an episodic request.
     */
    protected String generateSignature(String secret, Map<String, String> map) {
        return sha256Hex(buildSignatureString(secret, map));
    }

    String buildSignatureString(String secret, Map<String, String> map) {
        StringBuilder builder = new StringBuilder(secret);

        Set<String> keys = sortedKeys(map);
        for (String key : keys) {
            builder.append(key).append('=').append(map.get(key));
        }

        return builder.toString();
    }

    // split out because episode map method was too complex for intellij

    @SuppressWarnings({"ConstantConditions"})
    private Map<String, String> buildEpisodeMap(String[] showIds, String[] episodeIds, String searchTerm,
                                                SearchType searchType, TagMode tagMode, Episode.EpisodeStatus status,
                                                SortBy sortBy, SortDir sortDir, Boolean includeViews, Integer page,
                                                Integer perPage, Integer embedWidth, Integer embedHeight) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("expires", expires());
        if (showIds != null) map.put("show_id", join(showIds));
        if (episodeIds != null) map.put("id", join(episodeIds));
        if (searchTerm != null) map.put("search_type", searchType.name());
        if (tagMode != null) map.put("tag_mode", tagMode.name());
        if (status != null) map.put("status", status.name());
        if (sortBy != null) map.put("sort_by", sortBy.name());
        if (sortDir != null) map.put("sort_dir", sortDir.name());
        if (includeViews != null) map.put("include_views", includeViews.toString());
        if (page != null) map.put("page", page.toString());
        if (perPage != null) map.put("per_page", perPage.toString());
        if (embedWidth != null) map.put("embed_width", embedWidth.toString());
        if (embedHeight != null) map.put("embed_height", embedHeight.toString());
        return map;
    }


    private URI buildQueryURI(String call, Map<String, String> params) {
        List<NameValuePair> qparams = toNameValuePairList(params);
        try {
            return URIUtils.createURI(SCHEME, HOST, PORT, QUERY_API_PREFIX + call, URLEncodedUtils.format(qparams, "UTF-8"), null);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    private URI buildWriteURI(String call) {
        try {
            return URIUtils.createURI(SCHEME, HOST, PORT, WRITE_API_PREFIX + call, null, null);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    private void checkStatus(HttpResponse response) {
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() != 200) {
            throw new EpisodicException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }
    }


}

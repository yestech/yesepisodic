package org.yestech.episodic;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.yestech.episodic.objectmodel.*;
import static org.yestech.episodic.util.EpisodicUtil.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import static java.lang.String.valueOf;
import java.util.*;


/**
 * Default implementation of Episodic service.
 * <p/>
 * Requests are made using apache commons http client. The responses are handled with jaxb.
 *
 * @author A.J. Wright
 */
public class DefaultEpisodicService implements EpisodicService {


    protected static final String WRITE_API_PREFIX = "http://api.episodic.com/api/v2/write/";
    protected static final String QUERY_API_PREFIX = "http://api.episodic.com/api/v2/query/";

    protected String apiKey;
    protected String secret;
    protected HostConfiguration configuration;

    /**
     * Creates a new instance .
     *
     * @param secret The Secret key assigned to the user
     * @param apiKey The API key assigned to the user
     */
    public DefaultEpisodicService(String secret, String apiKey) {
        this.secret = secret;
        this.apiKey = apiKey;
        configuration = new HostConfiguration();
    }

    public DefaultEpisodicService(String secret, String apiKey, String proxyHost, int proxyPort) {
        this.secret = secret;
        this.apiKey = apiKey;
        configuration = new HostConfiguration();
        configuration.setProxy(proxyHost, proxyPort);
    }

    public String createAsset(String showId, String name, File file, String... tags) {

        PostMethod method = new PostMethod(WRITE_API_PREFIX + "create_asset");


        try {

            Map<String, String> map = new HashMap<String, String>();
            map.put("expires", expires());
            map.put("name", name);

            if (tags != null && tags.length > 0) {
                String tagString = join(tags);
                map.put("tags", tagString);
            }
            map.put("show_id", valueOf(showId));
            List<Part> parts = new ArrayList<Part>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                parts.add(new StringPart(entry.getKey(), entry.getValue()));
            }
            parts.add(new StringPart("signature", generateSignature(secret, map)));
            parts.add(new StringPart("key", apiKey));
            parts.add(new FilePart("uploaded_data", file));

            Part[] partArray = parts.toArray(new Part[parts.size()]);

            MultipartRequestEntity mre = new MultipartRequestEntity(partArray, method.getParams());
            method.setRequestEntity(mre);

            HttpClient client = new HttpClient();

            client.executeMethod(configuration, method);

            if (method.getStatusCode() != 200) {
                throw new EpisodicException(method.getStatusCode(), method.getStatusText());
            }


            Object o = unmarshall(method.getResponseBodyAsStream());

            if (o instanceof ErrorResponse) {
                throw new EpisodicException((ErrorResponse) o);
            } else if (o instanceof CreateAssetResponse) {
                return ((CreateAssetResponse) o).getAssetId();
            } else {
                throw new IllegalStateException("unknown response : " + o);
            }


        } catch (IOException e) {
            throw new TransportException(e.getMessage(), e);
        } catch (JAXBException e) {
            throw new TransportException(e.getMessage(), e);
        } finally {
            method.releaseConnection();
        }

    }

    public String createEpisode(String showId, String name, String[] assetIds, boolean publish, String description,
                                String pingUrl, String... tags) {


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

        PostMethod method = new PostMethod(WRITE_API_PREFIX + "create_episode");
        method.setParameter("key", apiKey);
        method.setParameter("signature", generateSignature(secret, map));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            method.setParameter(entry.getKey(), entry.getValue());
        }

        try {
            HttpClient client = new HttpClient();
            client.executeMethod(configuration, method);

            if (method.getStatusCode() != 200) {
                throw new EpisodicException(method.getStatusCode(), method.getStatusText());
            }

            Object o = unmarshall(method.getResponseBodyAsStream());

            if (o instanceof ErrorResponse) {
                throw new EpisodicException((ErrorResponse) o);
            } else if (o instanceof CreateEpisodeResponse) {
                return ((CreateEpisodeResponse) o).getEpisodeId();
            } else {
                throw new IllegalStateException("unknown response : " + o);
            }


        } catch (JAXBException e) {
            throw new TransportException(e.getMessage(), e);
        } catch (IOException e) {
            throw new TransportException(e.getMessage(), e);
        } finally {
            method.releaseConnection();
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

        NameValuePair[] queryParams = toNameValuePairArray(map);

        GetMethod method = new GetMethod(QUERY_API_PREFIX + "shows");
        method.setQueryString(queryParams);


        try {
            HttpClient client = new HttpClient();
            client.executeMethod(configuration, method);

            if (method.getStatusCode() != 200) {
                throw new EpisodicException(method.getStatusCode(), method.getStatusText());
            }

            Object o = unmarshall(method.getResponseBodyAsStream());

            if (o instanceof ErrorResponse) {
                throw new EpisodicException((ErrorResponse) o);
            } else if (o instanceof Shows) {
                return (Shows) o;
            } else {
                throw new IllegalStateException("unknown response : " + o);
            }


        } catch (JAXBException e) {
            throw new TransportException(e.getMessage(), e);
        } catch (IOException e) {
            throw new TransportException(e.getMessage(), e);
        } finally {
            method.releaseConnection();
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

        NameValuePair[] queryParams = toNameValuePairArray(map);


        GetMethod method = new GetMethod(QUERY_API_PREFIX + "episodes");
        method.setQueryString(queryParams);

        try {
            HttpClient client = new HttpClient();
            client.executeMethod(configuration, method);

            if (method.getStatusCode() != 200) {
                throw new EpisodicException(method.getStatusCode(), method.getStatusText());
            }

            Object o = unmarshall(method.getResponseBodyAsStream());

            if (o instanceof ErrorResponse) {
                throw new EpisodicException((ErrorResponse) o);
            } else if (o instanceof Episodes) {
                return (Episodes) o;
            } else {
                throw new IllegalStateException("unknown response : " + o);
            }


        } catch (JAXBException e) {
            throw new TransportException(e.getMessage(), e);
        } catch (IOException e) {
            throw new TransportException(e.getMessage(), e);
        } finally {
            method.releaseConnection();
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


}

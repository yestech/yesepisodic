package org.yestech.episodic;

import static org.apache.commons.codec.digest.DigestUtils.shaHex;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.yestech.episodic.objectmodel.CreateAssetResponse;
import org.yestech.episodic.objectmodel.CreateEpisodeResponse;
import org.yestech.episodic.objectmodel.ErrorResponse;
import org.yestech.episodic.objectmodel.Shows;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.String.format;
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
    protected static final String QUERY_API_PREFIX = "http://api.episodic.com/api/v2/query/shows/";

    protected String apiKey;
    protected String secret;

    /**
     * Creates a new instance .
     *
     * @param secret The Secret key assigned to the user
     * @param apiKey The API key assigned to the user
     */
    public DefaultEpisodicService(String secret, String apiKey) {
        this.secret = secret;
        this.apiKey = apiKey;
    }

    public long createAsset(String showId, String name, File file, String... tags) throws IOException {

        PostMethod method = new PostMethod(WRITE_API_PREFIX + "create_asset");

        Map<String, String> map = new HashMap<String, String>();
        map.put("expires", expires());
        map.put("name", name);

        if (tags != null && tags.length > 0) {
            String tagString = join(tags);
            map.put("tags", tagString);
        }

        map.put("show_id", showId);

        List<Part> parts = new ArrayList<Part>();
        parts.add(new StringPart("signature", generateSignature(secret, map)));
        parts.add(new StringPart("key", apiKey));
        parts.add(new FilePart("uploaded_data", file));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            parts.add(new StringPart(entry.getKey(), entry.getValue()));
        }

        Part[] partArray = parts.toArray(new Part[parts.size()]);

        MultipartRequestEntity mre = new MultipartRequestEntity(partArray, method.getParams());
        method.setRequestEntity(mre);

        HttpClient client = new HttpClient();

        client.executeMethod(method);

        if (method.getStatusCode() != 200) {
            throw new EpisodicException(format("Server returned %s : %s", method.getStatusCode(), method.getStatusText()));
        }


        try {
            Object o = unmarshall(method.getResponseBodyAsStream());

            if (o instanceof ErrorResponse) {
                throw new EpisodicException((ErrorResponse) o);
            } else if (o instanceof CreateAssetResponse) {
                return ((CreateAssetResponse) o).getAssetId();
            } else {
                throw new IllegalStateException("unknown response : " + o);
            }


        } catch (JAXBException e) {
            throw new TransportException(e.getMessage(), e);
        } finally {
            method.releaseConnection();
        }

    }

    public long createEpisode(String showId, String name, String[] assetIds, boolean publish, String description,
                              String pingUrl, String... tags) {


        Map<String, String> map = new HashMap<String, String>();
        map.put("expires", expires());
        map.put("name", name);

        if (tags != null && tags.length > 0) {
            String tagString = join(tags);
            map.put("tags", tagString);
        }

        map.put("show_id", showId);
        map.put("publish", valueOf(publish));
        if (description != null) map.put("description", description);
        if (pingUrl != null) map.put("ping_url", pingUrl);

        map.put("asset_ids", join(assetIds));
        map.put("ping_url", pingUrl);

        GetMethod method = new GetMethod(WRITE_API_PREFIX + "create_episode");
        method.getParams().setParameter("apiKey", apiKey);
        method.getParams().setParameter("signature", generateSignature(secret, map));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            method.getParams().setParameter(entry.getKey(), entry.getValue());
        }


        try {
            HttpClient client = new HttpClient();
            client.executeMethod(method);

            if (method.getStatusCode() != 200) {
                throw new EpisodicException(format("Server returned %s : %s", method.getStatusCode(), method.getStatusText()));
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

    public Shows getShows(SortBy sortBy, SortDir sortDir, Integer page, Integer perPage, String... showIds) {


        Map<String, String> map = new HashMap<String, String>();
        map.put("expires", expires());
        if (sortBy != null) map.put("sort_by", sortBy.name());
        if (sortDir != null) map.put("sort_dir", sortDir.name());
        if (page != null) map.put("page", page.toString());
        if (perPage != null) map.put("per_page", perPage.toString());
        if (showIds != null && showIds.length > 0) map.put("id", join(showIds));

        GetMethod method = new GetMethod();
        method.getParams().setParameter("apiKey", apiKey);
        method.getParams().setParameter("signature", generateSignature(secret, map));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            method.getParams().setParameter(entry.getKey(), entry.getValue());
        }


        try {
            HttpClient client = new HttpClient();
            client.executeMethod(method);

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
            throw new EpisodicException(e.getMessage(), e);
        } catch (IOException e) {
            throw new TransportException(e.getMessage(), e);
        } finally {
            method.releaseConnection();
        }

    }

    
    protected String generateSignature(String secret, Map<String, String> map) {
        StringBuilder builder = new StringBuilder(secret);

        Set<String> keys = new TreeSet<String>(map.keySet());
        for (String key : keys) {
            builder.append(key).append('=').append(map.get(key));
        }

        return shaHex(builder.toString());
    }

    /**
     * Generates the expires value needed to add to episodic reuqests.
     * <p/>
     * The current number of seconds since epoch (January 1, 1970).
     *
     * @return The current number of seconds since the epoch;
     */
    protected String expires() {
        return valueOf(System.currentTimeMillis() / 1000L);
    }

    /**
     * A simple method for joining an array of strings to a comma seperated string.
     *
     * @param strings An array of strings.
     * @return The array as single string joined by commas
     */
    protected String join(String[] strings) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            builder.append(strings[i]);
            if (i + 1 < strings.length) builder.append(",");
        }
        return builder.toString();
    }

    /**
     * Unmarshalls the input stream into an object from org.yestech.episodic.objectmodel.
     *
     * @param content input stream containing xml content.
     * @return The unmarshalled object.
     * @throws JAXBException Thrown if there are issues with the xml stream passed in.
     */
    protected Object unmarshall(InputStream content) throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(Error.class,
                CreateAssetResponse.class,
                CreateEpisodeResponse.class,
                Shows.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        return unmarshaller.unmarshal(content);
    }

}

package org.yestech.episodic.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yestech.episodic.objectmodel.*;
import org.joda.time.DateTime;

import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import static java.lang.String.valueOf;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * @author A.J. Wright
 */
public final class EpisodicUtil {

    private static Logger logger = LoggerFactory.getLogger(EpisodicUtil.class);

    private static JAXBContext ctx;

    static {
        try {
            ctx = JAXBContext.newInstance(
                    ErrorResponse.class,
                    CreateAssetResponse.class,
                    CreateEpisodeResponse.class,
                    Episodes.class,
                    Shows.class);
        } catch (JAXBException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private EpisodicUtil() {

    }

    /**
     * Unmarshalls the input stream into an object from org.yestech.episodic.objectmodel.
     *
     * @param response the response
     * @return The unmarshalled object.
     * @throws javax.xml.bind.JAXBException Thrown if there are issues with the xml stream passed in.
     */
    public static Object unmarshall(HttpResponse response) throws JAXBException, IOException {
        String xml = EntityUtils.toString(response.getEntity());
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        return unmarshaller.unmarshal(new StringReader(xml));
    }

    /**
     * A simple method for joining an array of strings to a comma seperated string.
     *
     * @param strings An array of strings.
     * @return The array as single string joined by commas
     */
    public static String join(String[] strings) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            builder.append(strings[i]);
            if (i + 1 < strings.length) builder.append(",");
        }
        return builder.toString();
    }

    /**
     * Generates the expires value needed to add to episodic requests.
     * <p/>
     * The current number of seconds since epoch (January 1, 1970).
     * <p/>
     * I hacked this with a 1 day buffer because of problems with timezones etc.
     *
     * @return The current number of seconds since the epoch;
     */
    public static String expires() {
        DateTime dt = new DateTime(DateTimeZone.UTC).plusMinutes(15);
        return valueOf(dt.getMillis() / 1000L);
    }

    /**
     * Returns the keys for the map in a set sorted alphabetically.
     * <p/>
     * This is used to help build the signature.
     *
     * @param map The map to get the keys for.
     * @return The keys sorted alphabetically.
     */
    public static SortedSet<String> sortedKeys(Map<String, String> map) {
        return new TreeSet<String>(map.keySet());
    }

    public static List<NameValuePair> toNameValuePairList(Map<String, String> map) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return pairs;
    }


}

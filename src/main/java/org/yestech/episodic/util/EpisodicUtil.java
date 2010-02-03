package org.yestech.episodic.util;

import org.joda.time.DateTimeZone;
import org.yestech.episodic.objectmodel.ErrorResponse;
import org.yestech.episodic.objectmodel.CreateAssetResponse;
import org.yestech.episodic.objectmodel.CreateEpisodeResponse;
import org.yestech.episodic.objectmodel.Shows;
import org.apache.commons.httpclient.NameValuePair;
import org.joda.time.DateTime;

import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import static java.lang.String.valueOf;

import java.util.*;

/**
 * @author A.J. Wright
 */
public final class EpisodicUtil {

    private EpisodicUtil() {

    }

    /**
     * Unmarshalls the input stream into an object from org.yestech.episodic.objectmodel.
     *
     * @param content input stream containing xml content.
     * @return The unmarshalled object.
     * @throws javax.xml.bind.JAXBException Thrown if there are issues with the xml stream passed in.
     */
    public static Object unmarshall(InputStream content) throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                ErrorResponse.class,
                CreateAssetResponse.class,
                CreateEpisodeResponse.class,
                Shows.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        return unmarshaller.unmarshal(content);
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
     *
     * Actually there is a 10 minute buffer on this so that it will not time out for long uploads.
     *
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

    public static NameValuePair[] toNameValuePairArray(Map<String,String> map) {
        NameValuePair[] pairs = new NameValuePair[map.size()];
        int count = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            pairs[count] = new NameValuePair(entry.getKey(), entry.getValue());
            count++;
        }
        return pairs;
    }


}

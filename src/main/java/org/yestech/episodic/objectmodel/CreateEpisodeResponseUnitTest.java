package org.yestech.episodic.objectmodel;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * @author A.J. Wright
 */
public class CreateEpisodeResponseUnitTest {

    @Test
    public void testUnmarshall() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(CreateEpisodeResponse.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        CreateEpisodeResponse cer = (CreateEpisodeResponse) unmarshaller.unmarshal(new StringReader(XML));
        assertNotNull(cer);
        assertEquals("success", cer.getResult());
        assertEquals("kpxqtcwrb6dd", cer.getEpisodeId());
    }

    private static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<create_episode_response result=\"success\" episode_id=\"kpxqtcwrb6dd\"/>";


}

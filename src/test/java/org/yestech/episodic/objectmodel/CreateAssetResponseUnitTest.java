package org.yestech.episodic.objectmodel;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

import static junit.framework.Assert.*;

/**
 * @author A.J. Wright
 */
public class CreateAssetResponseUnitTest {

    @Test
    public void testUnmarshall() throws JAXBException {

        JAXBContext ctx = JAXBContext.newInstance(CreateAssetResponse.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        CreateAssetResponse car = (CreateAssetResponse) unmarshaller.unmarshal(new StringReader(XML));
        assertNotNull(car);
        assertEquals("success", car.getResult());
        assertEquals("2856", car.getAssetId());


    }

    public static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<create_asset_response result=\"success\" asset_id=\"2856\"/>";



}

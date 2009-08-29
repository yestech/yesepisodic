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
public class ShowsUnitTest {

    @Test
    public void testUnmarshall() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(Shows.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();

        Shows shows = (Shows) unmarshaller.unmarshal(new StringReader(XML));
        assertNotNull(shows);
        assertEquals(500, shows.getPerPage());
        assertEquals(1, shows.getPages());
        assertEquals(1, shows.getPage());
        assertEquals(2, shows.getTotal());
        assertEquals(2, shows.getShow().size());

        Show show = shows.getShow().get(0);
        assertNull(show.getError());
        assertNotNull(show);
        assertEquals(1001, show.getId());
        assertEquals("My Show", show.getName());
        assertEquals("My Show is about things that are both great and awesome!", show.getDescription());
        assertEquals("asldkfjasdflj", show.getPageUrl());
        assertEquals("sdfsdfsdfd32", show.getItunesUrl());

        Format format = show.getFormat();
        assertNotNull(format);
        assertEquals("Widescreen Large", format.getName());
        assertEquals(848, format.getWidth());
        assertEquals(480, format.getHeight());
        assertEquals("[Thumbnail URL Goes Here]", format.getThumbnailUrl());
        assertNotNull(format.getPlayer());
        assertEquals(2, format.getPlayer().size());

        Player player = format.getPlayer().get(0);
        assertNotNull(player);
        assertEquals("My Custom Player", player.getName());
        assertEquals(true, player.isDefaultPlayer());
        assertEquals("[Escaped Embed Code Goes Here]", player.getEmbed());
        assertEquals("[Escaped Config URL Goes Here]", player.getConfig());

        player = format.getPlayer().get(1);
        assertNotNull(player);
        assertEquals("Default Player", player.getName());
        assertEquals(false, player.isDefaultPlayer());
        assertEquals("[Escaped Embed Code Goes Here]", player.getEmbed());
        assertEquals("[Escaped Config URL Goes Here]", player.getConfig());

        show = shows.getShow().get(1);
        assertNotNull(show);
        assertEquals(1002, show.getId());
        assertNull(show.getName());
        assertNull(show.getDescription());
        assertNull(show.getPageUrl());
        assertNull(show.getItunesUrl());
        assertNull(show.getFormat());

        ErrorResponse errorResponse = show.getError();
        assertNotNull(errorResponse);
        assertEquals(6, errorResponse.getCode());
        assertEquals("Show not found", errorResponse.getMessage());
    }


    public static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<shows per_page=\"500\" pages=\"1\" page=\"1\" total=\"2\">\n" +
            "  <show>\n" +
            "    <id>1001</id>\n" +
            "    <name>My Show</name>\n" +
            "    <description>My Show is about things that are both great and awesome!</description>\n" +
            "    <page_url>asldkfjasdflj</page_url>\n" +
            "    <itunes_url>sdfsdfsdfd32</itunes_url>\n" +
            "    <format>\n" +
            "      <name>Widescreen Large</name>\n" +
            "      <width>848</width>\n" +
            "      <height>480</height>\n" +
            "      <thumbnail_url>[Thumbnail URL Goes Here]</thumbnail_url>\n" +
            "      <player>\n" +
            "        <name>My Custom Player</name>\n" +
            "        <defaut>true</defaut>\n" +
            "        <embed>[Escaped Embed Code Goes Here]</embed>\n" +
            "        <config>[Escaped Config URL Goes Here]</config>\n" +
            "      </player>\n" +
            "      <player>\n" +
            "        <name>Default Player</name>\n" +
            "        <defaut>false</defaut>\n" +
            "        <embed>[Escaped Embed Code Goes Here]</embed>\n" +
            "        <config>[Escaped Config URL Goes Here]</config>\n" +
            "      </player>\n" +
            "    </format>\n" +
            "  </show>\n" +
            "  <show>\n" +
            "    <id>1002</id>\n" +
            "    <error>\n" +
            "      <code>6</code>\n" +
            "      <message>Show not found</message>\n" +
            "    </error>\n" +
            "  </show>\n" +
            "</shows>\n" +
            "\t\t";


}

package org.yestech.episodic.objectmodel;

import static junit.framework.Assert.*;
import org.joda.time.DateTime;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * @author A.J. Wright
 */
public class EpisodesUnitTest {

    @Test
    public void testUnmarshall() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(Episodes.class);

        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        Episodes episodes = (Episodes) unmarshaller.unmarshal(new StringReader(XML));
        assertNotNull(episodes);
        assertEquals(1, episodes.getPages());
        assertEquals(2, episodes.getTotal());
        assertEquals(20, episodes.getPerPage());
        assertEquals(1, episodes.getPage());
        assertEquals(2, episodes.getEpisode().size());

        Episode e = episodes.getEpisode().get(0);
        assertEquals(10, e.getId());
        assertEquals("My Fun Episode", e.getName());
        assertEquals("This is an episode about fun things.", e.getDescription());
        assertEquals(0, e.getTags().length);
        DateTime d = e.getAirDate();
        assertNotNull(d);
        assertEquals(2008, d.getYear());
        assertEquals(12, d.getMonthOfYear());
        assertEquals(8, d.getDayOfMonth());
        assertEquals(17, d.getHourOfDay());
        assertEquals(36, d.getMinuteOfHour());
        assertEquals(22, d.getSecondOfMinute());
        assertEquals("01:39:10", e.getDuration());
        assertEquals(Episode.EpisodeStatus.on_the_air, e.getStatus());
        assertNotNull(e.getThumbnails());
        assertFalse("Thumbnails are empty", e.getThumbnails().isEmpty());
        assertEquals(4, e.getThumbnails().size());
        Thumbnail t = e.getThumbnails().get(0);
        assertNotNull(t);
        assertEquals(480, t.getWidth());
        assertEquals(360, t.getHeight());
        assertEquals("[Thumbnail URL Goes Here]", t.getUrl());
        t = e.getThumbnails().get(1);
        assertNotNull(t);
        assertEquals(640, t.getWidth());
        assertEquals(480, t.getHeight());
        assertEquals("[Thumbnail URL Goes Here]", t.getUrl());
        t = e.getThumbnails().get(2);
        assertNotNull(t);
        assertEquals(640, t.getWidth());
        assertEquals(360, t.getHeight());
        assertEquals("[Thumbnail URL Goes Here]", t.getUrl());
        t = e.getThumbnails().get(3);
        assertNotNull(t);
        assertEquals(640, t.getWidth());
        assertEquals(481, t.getHeight());
        assertEquals("[Thumbnail URL Goes Here]", t.getUrl());
        Players players = e.getPlayers();
        assertEquals(480, players.getWidth());
        assertEquals(360, players.getHeight());
        assertNotNull(e.getPlayers().getPlayers());
        assertEquals(2, e.getPlayers().getPlayers().size());
        Player p = e.getPlayers().getPlayers().get(0);
        assertEquals(true, p.isDefaultPlayer());
        assertEquals("My Custom Player", p.getName());
        assertEquals("[Escaped Embed Code Goes Here]", p.getEmbedCode());
        assertEquals("[Escaped Config URL Goes Here]", p.getConfig());
        p = e.getPlayers().getPlayers().get(1);
        assertEquals(false, p.isDefaultPlayer());
        assertEquals("Default Player", p.getName());
        assertEquals("[Escaped Embed Code Goes Here]", p.getEmbedCode());
        assertEquals("[Escaped Config URL Goes Here]", p.getConfig());
        assertNotNull(e.getDownloads());
        assertEquals(1, e.getDownloads().size());
        Download download = e.getDownloads().get(0);
        assertNotNull(download);
        assertEquals(480, download.getWidth());
        assertEquals(360, download.getHeight());
        assertEquals("[MP4 URL Goes Here]", download.getUrl());

        e = episodes.getEpisode().get(1);
        assertEquals(28, e.getId());
        assertEquals("My Other Episode", e.getName());
        assertEquals("", e.getDescription());
        String[] tags = e.getTags();
        assertEquals(3, tags.length);
        assertEquals("fun", tags[0]);
        assertEquals("exciting", tags[1]);
        assertEquals("awesome", tags[2]);
        d = e.getAirDate();
        assertNotNull(d);
        assertEquals(2009, d.getYear());
        assertEquals(6, d.getMonthOfYear());
        assertEquals(8, d.getDayOfMonth());
        assertEquals(21, d.getHourOfDay());
        assertEquals(41, d.getMinuteOfHour());
        assertEquals(29, d.getSecondOfMinute());


    }

    public static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<episodes pages=\"1\" total=\"2\" per_page=\"20\" page=\"1\">\n" +
            "  <episode>\n" +
            "    <id>10</id>\n" +
            "    <name>My Fun Episode</name>\n" +
            "    <description>This is an episode about fun things.</description>\n" +
            "    <tags></tags>\n" +
            "    <air_date>2008-12-08 17:36:22</air_date>\n" +
            "    <duration>01:39:10</duration>\n" +
            "    <status>on_the_air</status>\n" +
            "    <thumbnails>\n" +
            "      <thumbnail width=\"480\" height=\"360\">[Thumbnail URL Goes Here]</thumbnail>\n" +
            "      <thumbnail width=\"640\" height=\"480\">[Thumbnail URL Goes Here]</thumbnail>\n" +
            "      <thumbnail width=\"640\" height=\"360\">[Thumbnail URL Goes Here]</thumbnail>\n" +
            "      <thumbnail width=\"640\" height=\"481\">[Thumbnail URL Goes Here]</thumbnail>\n" +
            "    </thumbnails>\n" +
            "    <players width=\"480\" height=\"360\">\n" +
            "      <player default=\"true\">\n" +
            "        <name>My Custom Player</name>\n" +
            "        <embed_code>[Escaped Embed Code Goes Here]</embed_code>\n" +
            "        <config>[Escaped Config URL Goes Here]</config>\n" +
            "      </player>\n" +
            "      <player default=\"false\">\n" +
            "        <name>Default Player</name>\n" +
            "        <embed_code>[Escaped Embed Code Goes Here]</embed_code>\n" +
            "        <config>[Escaped Config URL Goes Here]</config>\n" +
            "      </player>\n" +
            "    </players>\n" +
            "    <downloads>\n" +
            "      <download width=\"480\" height=\"360\">\n" +
            "        <url>[MP4 URL Goes Here]</url>\n" +
            "      </download>\n" +
            "    </downloads>\n" +
            "  </episode>\n" +
            "  <episode>\n" +
            "    <id>28</id>\n" +
            "    <name>My Other Episode</name>\n" +
            "    <description></description>\n" +
            "    <tags>fun,exciting,awesome</tags>\n" +
            "    <air_date>2009-06-08 21:41:29</air_date>\n" +
            "    <duration>01:59:14</duration>\n" +
            "    <status>on_the_air</status>\n" +
            "    <thumbnails>\n" +
            "      <thumbnail width=\"480\" height=\"360\">[Thumbnail URL Goes Here]</thumbnail>\n" +
            "      <thumbnail width=\"640\" height=\"480\">[Thumbnail URL Goes Here]</thumbnail>\n" +
            "      <thumbnail width=\"640\" height=\"360\">[Thumbnail URL Goes Here]</thumbnail>\n" +
            "      <thumbnail width=\"640\" height=\"480\">[Thumbnail URL Goes Here]</thumbnail>\n" +
            "    </thumbnails>\n" +
            "    <players width=\"640\" height=\"360\">\n" +
            "      <player default=\"true\">\n" +
            "        <name>My Custom Player</name>\n" +
            "        <embed_code>[Escaped Embed Code Goes Here]</embed_code>\n" +
            "        <config>[Escaped Config URL Goes Here]</config>\n" +
            "      </player>\n" +
            "      <player default=\"false\">\n" +
            "        <name>Default Player</name>\n" +
            "        <embed_code>[Escaped Embed Code Goes Here]</embed_code>\n" +
            "        <config>[Escaped Config URL Goes Here]</config>\n" +
            "      </player>\n" +
            "    </players>\n" +
            "    <downloads>\n" +
            "      <download width=\"480\" height=\"360\">\n" +
            "        <url>[MP4 URL Goes Here]</url>\n" +
            "      </download>\n" +
            "    </downloads>\n" +
            "  </episode>\n" +
            "</episodes>";
}

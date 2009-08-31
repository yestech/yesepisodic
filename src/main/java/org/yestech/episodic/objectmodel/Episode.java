package org.yestech.episodic.objectmodel;

import org.joda.time.DateTime;
import org.yestech.episodic.util.DateXmlAdapter;
import org.yestech.episodic.util.CommaSeperatedStringArrayXmlConverter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.List;

/**
 * @author A.J. Wright
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "episode")
public class Episode implements Serializable {

    public static enum EpisodeStatus {
        off_the_air,
        publishing,
        on_the_air,
        waiting_to_air,
        publish_failed
    }

    @XmlElement
    protected long id;
    @XmlElement
    protected String name;
    @XmlElement
    protected String description;
    @XmlElement
    @XmlJavaTypeAdapter(CommaSeperatedStringArrayXmlConverter.class)
    protected String[] tags;
    @XmlElement(name = "air_date")
    @XmlJavaTypeAdapter(DateXmlAdapter.class)
    protected DateTime airDate;
    @XmlElement
    protected String duration;
    @XmlElement
    protected EpisodeStatus status;
    @XmlElementWrapper(name = "thumbnails")
    @XmlElements({
            @XmlElement(name = "thumbnail", type = Thumbnail.class)
    })
    protected List<Thumbnail> thumbnails;
    protected Players players;
    @XmlElementWrapper(name = "downloads")
    @XmlElements({
            @XmlElement(name = "download", type = Download.class)
    })
    protected List<Download> downloads;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public DateTime getAirDate() {
        return airDate;
    }

    public void setAirDate(DateTime airDate) {
        this.airDate = airDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public EpisodeStatus getStatus() {
        return status;
    }

    public void setStatus(EpisodeStatus status) {
        this.status = status;
    }

    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<Thumbnail> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public Players getPlayers() {
        return players;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }

    public List<Download> getDownloads() {
        return downloads;
    }

    public void setDownloads(List<Download> downloads) {
        this.downloads = downloads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Episode episode = (Episode) o;

        if (id != episode.id) return false;
        if (airDate != null ? !airDate.equals(episode.airDate) : episode.airDate != null) return false;
        if (description != null ? !description.equals(episode.description) : episode.description != null) return false;
        if (downloads != null ? !downloads.equals(episode.downloads) : episode.downloads != null) return false;
        if (duration != null ? !duration.equals(episode.duration) : episode.duration != null) return false;
        if (name != null ? !name.equals(episode.name) : episode.name != null) return false;
        if (players != null ? !players.equals(episode.players) : episode.players != null) return false;
        if (status != episode.status) return false;
        if (tags != null ? !tags.equals(episode.tags) : episode.tags != null) return false;
        //noinspection RedundantIfStatement
        if (thumbnails != null ? !thumbnails.equals(episode.thumbnails) : episode.thumbnails != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (airDate != null ? airDate.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (thumbnails != null ? thumbnails.hashCode() : 0);
        result = 31 * result + (players != null ? players.hashCode() : 0);
        result = 31 * result + (downloads != null ? downloads.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                ", airDate=" + airDate +
                ", duration='" + duration + '\'' +
                ", status=" + status +
                ", thumbnails=" + thumbnails +
                ", players=" + players +
                ", downloads=" + downloads +
                '}';
    }
}

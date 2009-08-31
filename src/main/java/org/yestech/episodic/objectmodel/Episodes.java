package org.yestech.episodic.objectmodel;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author A.J. Wright
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "episodes")
public class Episodes implements Serializable {
    @XmlAttribute(required = true)
    protected long page;
    @XmlAttribute(required = true)
    protected long pages;
    @XmlAttribute(name = "per_page", required = true)
    protected long perPage;
    @XmlAttribute(required = true)
    protected long total;

    @XmlElement
    private List<Episode> episode;

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getPerPage() {
        return perPage;
    }

    public void setPerPage(long perPage) {
        this.perPage = perPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Episode> getEpisode() {
        return episode;
    }

    public void setEpisode(List<Episode> episode) {
        this.episode = episode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Episodes episodes1 = (Episodes) o;

        if (page != episodes1.page) return false;
        if (pages != episodes1.pages) return false;
        if (perPage != episodes1.perPage) return false;
        if (total != episodes1.total) return false;
        //noinspection RedundantIfStatement
        if (episode != null ? !episode.equals(episodes1.episode) : episodes1.episode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (page ^ (page >>> 32));
        result = 31 * result + (int) (pages ^ (pages >>> 32));
        result = 31 * result + (int) (perPage ^ (perPage >>> 32));
        result = 31 * result + (int) (total ^ (total >>> 32));
        result = 31 * result + (episode != null ? episode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Episodes{" +
                "page=" + page +
                ", pages=" + pages +
                ", perPage=" + perPage +
                ", total=" + total +
                ", episode=" + episode +
                '}';
    }
}

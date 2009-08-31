package org.yestech.episodic.objectmodel;

import org.yestech.episodic.objectmodel.Show;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "shows")
public class Shows implements Serializable {

    @XmlElement(required = true)
    protected List<Show> show;
    @XmlAttribute(required = true)
    protected long page;
    @XmlAttribute(required = true)
    protected long pages;
    @XmlAttribute(name = "per_page", required = true)
    protected long perPage;
    @XmlAttribute(required = true)
    protected long total;

    /**
     * Gets the value of the show property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the show property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShow().add(newItem);
     * </pre>
     *
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Show }
     *
     *
     * @return shows
     */
    public List<Show> getShow() {
        if (show == null) {
            show = new ArrayList<Show>();
        }
        return this.show;
    }


    public long getPage() {
        return page;
    }

    public void setPage(long value) {
        this.page = value;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long value) {
        this.pages = value;
    }

    public long getPerPage() {
        return perPage;
    }

    public void setPerPage(long value) {
        this.perPage = value;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long value) {
        this.total = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shows shows = (Shows) o;

        if (page != shows.page) return false;
        if (pages != shows.pages) return false;
        if (perPage != shows.perPage) return false;
        if (total != shows.total) return false;
        if (show != null ? !show.equals(shows.show) : shows.show != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = show != null ? show.hashCode() : 0;
        result = 31 * result + (int) (page ^ (page >>> 32));
        result = 31 * result + (int) (pages ^ (pages >>> 32));
        result = 31 * result + (int) (perPage ^ (perPage >>> 32));
        result = 31 * result + (int) (total ^ (total >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Shows{" +
                "show=" + show +
                ", page=" + page +
                ", pages=" + pages +
                ", perPage=" + perPage +
                ", total=" + total +
                '}';
    }
}

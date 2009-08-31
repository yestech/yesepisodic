package org.yestech.episodic.objectmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "show")
public class Show implements Serializable {

    @XmlElement(required = true)
    protected String id;

    @XmlElement
    protected String name;

    @XmlElement
    protected String description;

    @XmlElement
    protected ErrorResponse error;

    @XmlElement(name = "itunes_url")
    protected String itunesUrl;

    @XmlElement(name = "page_url")
    protected String pageUrl;

    @XmlElement(name = "format")
    protected Format format;

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse value) {
        this.error = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItunesUrl() {
        return itunesUrl;
    }

    public void setItunesUrl(String itunesUrl) {
        this.itunesUrl = itunesUrl;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Show show = (Show) o;

        if (description != null ? !description.equals(show.description) : show.description != null) return false;
        if (error != null ? !error.equals(show.error) : show.error != null) return false;
        if (format != null ? !format.equals(show.format) : show.format != null) return false;
        if (id != null ? !id.equals(show.id) : show.id != null) return false;
        if (itunesUrl != null ? !itunesUrl.equals(show.itunesUrl) : show.itunesUrl != null) return false;
        if (name != null ? !name.equals(show.name) : show.name != null) return false;
        //noinspection RedundantIfStatement
        if (pageUrl != null ? !pageUrl.equals(show.pageUrl) : show.pageUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (itunesUrl != null ? itunesUrl.hashCode() : 0);
        result = 31 * result + (pageUrl != null ? pageUrl.hashCode() : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", error=" + error +
                ", itunesUrl='" + itunesUrl + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", format=" + format +
                '}';
    }
}

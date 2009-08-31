package org.yestech.episodic.objectmodel;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * @author A.J. Wright
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "shows")
public class Download implements Serializable {

    @XmlAttribute
    protected int width;
    @XmlAttribute
    protected int height;
    @XmlElement
    protected String url;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Download download = (Download) o;

        if (height != download.height) return false;
        if (width != download.width) return false;
        //noinspection RedundantIfStatement
        if (url != null ? !url.equals(download.url) : download.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Download{" +
                "width=" + width +
                ", height=" + height +
                ", url='" + url + '\'' +
                '}';
    }
}

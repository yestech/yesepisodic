package org.yestech.episodic.objectmodel;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * @author A.J. Wright
 */
@XmlRootElement(name = "create_episode_response")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateEpisodeResponse implements Serializable {

    @XmlAttribute
    protected String result;

    @XmlAttribute(name = "episode_id")
    protected String episodeId;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateEpisodeResponse that = (CreateEpisodeResponse) o;

        if (episodeId != null ? !episodeId.equals(that.episodeId) : that.episodeId != null) return false;
        //noinspection RedundantIfStatement
        if (result != null ? !result.equals(that.result) : that.result != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = result != null ? result.hashCode() : 0;
        result1 = 31 * result1 + (episodeId != null ? episodeId.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "CreateAssetResponse{" +
                "result='" + result + '\'' +
                ", episodeId=" + episodeId +
                '}';
    }
}
package org.yestech.episodic.objectmodel;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "player")
public class Player implements Serializable {

    @XmlElement(required = true)
    protected String name;
    @XmlAttribute(name = "default", required = true)
    protected boolean defaultPlayer;
    @XmlElement(name = "embed_code", required = true)
    protected String embedCode;
    @XmlElement(required = true)
    protected String config;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public boolean isDefaultPlayer() {
        return defaultPlayer;
    }

    public void setDefaultPlayer(boolean value) {
        this.defaultPlayer = value;
    }

    public String getEmbedCode() {
        return embedCode;
    }

    public void setEmbedCode(String value) {
        this.embedCode = value;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String value) {
        this.config = value;
    }

}

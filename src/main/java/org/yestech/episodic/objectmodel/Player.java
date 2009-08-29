package org.yestech.episodic.objectmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "player")
public class Player implements Serializable {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(name = "defaut", required = true)
    protected boolean defaultPlayer;
    @XmlElement(required = true)
    protected String embed;
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

    public String getEmbed() {
        return embed;
    }

    public void setEmbed(String value) {
        this.embed = value;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String value) {
        this.config = value;
    }

}

package org.yestech.episodic.objectmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "show")
public class Show {

    @XmlElement(required = true)
    protected long id;
    protected ErrorResponse error;

    @XmlElement(name = "itunes_url")
    protected String itunesUrl;

    @XmlElement(name = "page_url")
    protected String pageUrl;

    @XmlElement(name = "format")
    protected boolean format;



    public long getId() {
        return id;
    }


    public void setId(long value) {
        this.id = value;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse value) {
        this.error = value;
    }

}

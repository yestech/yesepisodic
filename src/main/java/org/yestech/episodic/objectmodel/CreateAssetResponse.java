package org.yestech.episodic.objectmodel;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * Response type for the createAsset request.
 * 
 * @author A.J. Wright
 */
@XmlRootElement(name = "create_asset_response")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateAssetResponse implements Serializable {

    @XmlAttribute
    protected String result;

    @XmlAttribute(name = "asset_id")
    protected long assetId;

    /**
     * The string result returned from episodic. If the request was successful this should be "success"
     *
     * @return String result for the createAsset request.
     */
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Returns the id of the newly created asset.
     *
     * @return the id of the newly created asset.
     */
    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateAssetResponse that = (CreateAssetResponse) o;

        if (assetId != that.assetId) return false;
        //noinspection RedundantIfStatement
        if (result != null ? !result.equals(that.result) : that.result != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = result != null ? result.hashCode() : 0;
        result1 = 31 * result1 + (int) (assetId ^ (assetId >>> 32));
        return result1;
    }

    @Override
    public String toString() {
        return "CreateAssetResponse{" +
                "result='" + result + '\'' +
                ", assetId=" + assetId +
                '}';
    }
}

package org.yestech.episodic.util;

import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.james.mime4j.descriptor.ContentDescriptor;

public class EpisodicFormStringBodyPart extends FormBodyPart {

    public EpisodicFormStringBodyPart(String name, ContentBody body) {
        super(name, body);
    }

    @Override
    protected void generateContentType(ContentDescriptor desc) {
        // nothing
    }

    @Override
    protected void generateTransferEncoding(ContentDescriptor desc) {
        // nothing
    }
}

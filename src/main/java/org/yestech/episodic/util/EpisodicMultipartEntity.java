package org.yestech.episodic.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.james.mime4j.field.Fields;
import org.apache.james.mime4j.message.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Episodic seems to crap out if the following headers are included in the non file portions.
 * Content-Type: text/plain; charset=US-ASCII
 * Content-Transfer-Encoding: 8bit
 *
 * Had to copy the entire class because of private fields (they suck!)
 */
@ThreadSafe
public class EpisodicMultipartEntity implements HttpEntity {

    /**
     * The pool of ASCII chars to be used for generating a multipart boundary.
     */
    private final static char[] MULTIPART_CHARS =
        "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .toCharArray();

    private final Message message;
    private final HttpMultipart multipart;
    private final Header contentType;

    private long length;
    private volatile boolean dirty; // used to decide whether to recalculate length

    public EpisodicMultipartEntity(
            HttpMultipartMode mode,
            final String boundary,
            final Charset charset) {
        super();
        this.multipart = new HttpMultipart("form-data");
        this.contentType = new BasicHeader(
                HTTP.CONTENT_TYPE,
                generateContentType(boundary, charset));
        this.dirty = true;

        this.message = new Message();
        org.apache.james.mime4j.message.Header header =
          new org.apache.james.mime4j.message.Header();
        this.message.setHeader(header);
        this.multipart.setParent(message);
        if (mode == null) {
            mode = HttpMultipartMode.STRICT;
        }
        this.multipart.setMode(mode);
        this.message.getHeader().addField(Fields.contentType(this.contentType.getValue()));
    }

    public EpisodicMultipartEntity(final HttpMultipartMode mode) {
        this(mode, null, null);
    }

    public EpisodicMultipartEntity() {
        this(HttpMultipartMode.STRICT, null, null);
    }

    protected String generateContentType(
            final String boundary,
            final Charset charset) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("multipart/form-data; boundary=");
        if (boundary != null) {
            buffer.append(boundary);
        } else {
            Random rand = new Random();
            int count = rand.nextInt(11) + 30; // a random size from 30 to 40
            for (int i = 0; i < count; i++) {
                buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
            }
        }
        if (charset != null) {
            buffer.append("; charset=");
            buffer.append(charset.name());
        }
        return buffer.toString();
    }

    public void addPart(final String name, final ContentBody contentBody) {
        if (contentBody instanceof StringBody) {
            this.multipart.addBodyPart(new EpisodicFormStringBodyPart(name, contentBody));
        } else {
            this.multipart.addBodyPart(new FormBodyPart(name, contentBody));
        }
        this.dirty = true;
    }

    public boolean isRepeatable() {
        List<?> parts = this.multipart.getBodyParts();
        for (Iterator<?> it = parts.iterator(); it.hasNext(); ) {
            FormBodyPart part = (FormBodyPart) it.next();
            ContentBody body = (ContentBody) part.getBody();
            if (body.getContentLength() < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isChunked() {
        return !isRepeatable();
    }

    public boolean isStreaming() {
        return !isRepeatable();
    }

    public long getContentLength() {
        if (this.dirty) {
            this.length = this.multipart.getTotalLength();
            this.dirty = false;
        }
        return this.length;
    }

    public Header getContentType() {
        return this.contentType;
    }

    public Header getContentEncoding() {
        return null;
    }

    public void consumeContent()
        throws IOException, UnsupportedOperationException{
        if (isStreaming()) {
            throw new UnsupportedOperationException(
                    "Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException(
                    "Multipart form entity does not implement #getContent()");
    }

    public void writeTo(final OutputStream outstream) throws IOException {
        this.multipart.writeTo(outstream);
    }

}
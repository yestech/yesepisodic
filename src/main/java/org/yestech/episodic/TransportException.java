package org.yestech.episodic;

/**
 * Occurs when there are problems with io between episodic, or problems parsing episodic content.
 *
 * This exception will have a root cause of either: {@link java.io.IOException}, or {@link javax.xml.bind.JAXBException}
 *
 *
 * @author A.J. Wright
 */
public class TransportException extends RuntimeException {

    public TransportException(String message, Throwable cause) {
        super(message, cause);
    }
}

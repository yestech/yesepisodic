package org.yestech.episodic;

import org.yestech.episodic.objectmodel.ErrorResponse;

/**
 * Thrown when an Error Response is received from episodic.
 * <p/>
 * The following is a list of error codes that can be returned from Episodic API methods. <br />
 * 1 - The API Key wasn't provided or is invalid or the signature is invalid. <br />
 * 2 - The requested report could not be found. Either the report token is invalid or the report has expired and is no longer available. <br />
 * 3 - The request failed to specifiy one or more of the required parameters to an API method. <br />
 * 4 - The value specified for a parameter is not valid. <br />
 * 5 - The request is no longer valid because the expires parameter specifies a time more than 30 seconds old.<br />
 * 6 - The specified object (i.e. show or episode) could not be found.<br />
 * 7 - API access for the user is disabled. <br />
 * 500 - There was an unexpected error on the server. <br />
 *
 * @author A.J. Wright
 */
public class EpisodicException extends RuntimeException {

    protected int code;

    public EpisodicException(ErrorResponse er) {
        this(er.getCode(), er.getMessage());
    }

    public EpisodicException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}

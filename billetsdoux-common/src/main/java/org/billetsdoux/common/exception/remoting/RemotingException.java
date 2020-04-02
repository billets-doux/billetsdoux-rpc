package org.billetsdoux.common.exception.remoting;

public class RemotingException extends Exception {

    private static final long serialVersionUID = -298481855023495391L;

    public RemotingException(String message) {
        super(message);
    }

    public RemotingException(String message, Throwable cause) {
        super(message, cause);
    }
}

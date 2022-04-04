package id.ac.fksp.spmi.exception;

public class AnnouncementException extends RuntimeException {
    public AnnouncementException(String message) {
        super(message);
    }

    public AnnouncementException(String message, Throwable cause) {
        super(message, cause);
    }
}

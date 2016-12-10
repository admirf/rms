package utility;

/**
 * Created by admir on 10.12.2016..
 */
public class RMSException extends Exception {
    private String message;
    public RMSException(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

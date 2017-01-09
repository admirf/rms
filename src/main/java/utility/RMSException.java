package utility;

/**
 * Dummy exception template for future improvement
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

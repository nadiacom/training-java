package exceptions.daos;

/**
 * Created by ebiz on 14/03/17.
 */
public class DAOConfigurationException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param message (required) exception message.
     */
    public DAOConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message (required) exception message.
     * @param cause   (required) exception cause.
     */
    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause (required) exception cause.
     */
    public DAOConfigurationException(Throwable cause) {
        super(cause);
    }

}
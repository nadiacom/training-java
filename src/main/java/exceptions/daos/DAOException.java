package exceptions.daos;

/**
 * Created by ebiz on 14/03/17.
 */

public class DAOException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param message (required) exception message.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message (required) exception message.
     * @param cause   (required) exception cause.
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause (required) exception cause.
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

}
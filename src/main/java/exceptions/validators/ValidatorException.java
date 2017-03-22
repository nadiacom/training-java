package exceptions.validators;

/**
 * Created by ebiz on 22/03/17.
 */
public abstract class ValidatorException extends Exception {

    /**
     * ValidatorException.
     * @param msg String
     */
    protected ValidatorException(String msg) {
        super(msg);
    }
}

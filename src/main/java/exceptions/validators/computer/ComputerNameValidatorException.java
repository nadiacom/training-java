package exceptions.validators.computer;

import exceptions.validators.ValidatorException;

/**
 * Created by ebiz on 22/03/17.
 */
public class ComputerNameValidatorException extends ValidatorException {

    /**
     * Constructor ComputerNameValidatorException.
     * @param msg exception message.
     */
    public ComputerNameValidatorException(String msg) {
        super(msg);
    }
}

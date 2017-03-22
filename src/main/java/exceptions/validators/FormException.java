package exceptions.validators;

import exceptions.validators.ValidatorException;

/**
 * Created by ebiz on 22/03/17.
 */
public class FormException extends ValidatorException {

    /**
     * Constructor ComputerNameValidatorException.
     * @param msg exception message.
     */
    public FormException(String msg) {
        super(msg);
    }
}

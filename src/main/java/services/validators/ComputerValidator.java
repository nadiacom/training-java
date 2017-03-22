package services.validators;

import exceptions.validators.FormException;
import exceptions.validators.computer.ComputerCompanyValidatorException;
import exceptions.validators.computer.ComputerDiscontinuedValidatorException;
import exceptions.validators.computer.ComputerIntroducedValidatorException;
import exceptions.validators.computer.ComputerNameValidatorException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 * Created by ebiz on 21/03/17.
 */
public class ComputerValidator {

    Input input = new Input();

    /**
     * Check if computer input fields are valid or not and throw specified exceptions.
     *
     * @param request request with parameters.
     * @return boolean : computer valid or not.
     * @throws ComputerNameValidatorException         ComputerNameValidatorException.
     * @throws ComputerIntroducedValidatorException   ComputerIntroducedValidatorException.
     * @throws ComputerDiscontinuedValidatorException ComputerDiscontinuedValidatorException.
     * @throws ComputerCompanyValidatorException      ComputerCompanyValidatorException.
     * @throws FormException                          FormException.
     */
    public void isValidComputer(HttpServletRequest request) throws ComputerNameValidatorException, ComputerIntroducedValidatorException, ComputerDiscontinuedValidatorException, ComputerCompanyValidatorException, FormException {

        if (request.getParameterMap().containsKey("name") && request.getParameterMap().containsKey("introduced") && request.getParameterMap().containsKey("discontinued") && request.getParameterMap().containsKey("companyId")) {
            //If computer name is filled
            {
                if (!empty(request.getParameter("name"))) {
                    //Return if name is valid or not
                    if (!isValidName(request.getParameter("name"))) {
                        throw new ComputerNameValidatorException("Computer name is not valid.");
                    }
                } else {
                    //Else throw empty name exception
                    throw new ComputerNameValidatorException("Computer name is required.");
                }
            }
            //If computer introduced date is filled
            if (!empty(request.getParameter("introduced"))) {
                //Return if introduced date pattern is valid or not
                if (!isValidIntroduced(request.getParameter("introduced"))) {
                    throw new ComputerIntroducedValidatorException("Computer introduced date is not valid, expected pattern : YYYY-MM-dd");
                }
            }
            //If computer discontinued date is filled
            if (!empty(request.getParameter("discontinued"))) {
                //Return if discontinued date pattern is valid or not
                if (!isValidDiscontinued(request.getParameter("discontinued"))) {
                    throw new ComputerDiscontinuedValidatorException("Computer discontinued date is not valid, expected pattern : YYYY-MM-dd");
                }
                if (!isValidIntervalDate(request.getParameter("introduced"), request.getParameter("discontinued"))){
                    throw new ComputerDiscontinuedValidatorException("Please select a valid date interval. Discontinued date should be after introduced date.");
                }
            }
            //If computer company id is filled
            if (!empty(request.getParameter("companyId"))) {
                //Return if discontinued date pattern is valid or not
                if (!isValidCompanyId(request.getParameter("companyId"))) {
                    throw new ComputerCompanyValidatorException("Company id is not valid.");
                }
            }
        } else {
            throw new FormException("Oops, a form submission error occured, please re submit your form.");
        }
    }

    /**
     * Introduced date validator.
     *
     * @param date introduced date.
     * @return boolean : date valid or not.
     */
    public boolean isValidIntroduced(String date) {
        return input.isDatePatternValid(date);
    }

    /**
     * Discontinued date name validator.
     *
     * @param date discontinued date.
     * @return boolean : date valid or not.
     */
    public boolean isValidDiscontinued(String date) {
        return input.isDatePatternValid(date);
    }

    /**
     * Computer name validator.
     *
     * @param name computer name.
     * @return boolean : name valid or not.
     */
    public boolean isValidName(String name) {
        return true;
    }

    /**
     * Company id validator.
     *
     * @param companyId company id.
     * @return booean : company id valid or not.
     */
    public boolean isValidCompanyId(String companyId) {
        return true;
    }

    /**
     *
     * @param introduced introduced date.
     * @param discontinued discontinued date.
     * @return boolean : interval valid or not.
     */
    public boolean isValidIntervalDate(String introduced, String discontinued){
        LocalDate intro = input.getLocalDate(introduced);
        LocalDate discon = input.getLocalDate(discontinued);
        return discon.isAfter(intro);
    }

    /**
     * Check if string is empty.
     *
     * @param s input string.
     * @return bollean : empty or not.
     */
    public static boolean empty(final String s) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }
}

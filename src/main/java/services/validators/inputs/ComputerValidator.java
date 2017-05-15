package services.validators.inputs;

import exceptions.validators.FormException;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.daos.CompanyDaoImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public class ComputerValidator {

    private Input input = new Input();
    @Autowired
    private CompanyDaoImpl companyDaoIml;
    public StringBuilder error;
    private boolean first = true;

    /**
     * Check if computer input fields are valid or not and throw specified exceptions.
     *
     * @param request request with parameters.
     * @throws FormException FormException.
     */
    public void isValidComputer(HttpServletRequest request) throws FormException {

        error = new StringBuilder();

        if (request.getParameterMap().containsKey("name") && request.getParameterMap().containsKey("introduced") && request.getParameterMap().containsKey("discontinued") && request.getParameterMap().containsKey("companyId")) {
            //If computer name is filled
            {
                if (!empty(request.getParameter("name"))) {
                    //Return if name is valid or not
                    if (!isValidName(request.getParameter("name"))) {
                        if (first) {
                            error.append(System.getProperty("line.separator"));
                        }
                        error.append("Computer name is not valid.");
                        first = false;
                    }
                } else {
                    if (first) {
                        error.append(System.getProperty("line.separator"));
                    }
                    error.append("Computer name is required.");
                    first = false;
                }
            }
            //If computer introduced date is filled
            if (!empty(request.getParameter("introduced"))) {
                //Return if introduced date pattern is valid or not
                if (!isValidIntroduced(request.getParameter("introduced"))) {
                    if (first) {
                        error.append(System.getProperty("line.separator"));
                    }
                    error.append("Computer introduced date is not valid, expected pattern : YYYY-MM-dd");
                    first = false;
                }
            }
            //If computer discontinued date is filled
            if (!empty(request.getParameter("discontinued"))) {
                //Return if discontinued date pattern is valid or not
                if (!isValidDiscontinued(request.getParameter("discontinued"))) {
                    if (first) {
                        error.append(System.getProperty("line.separator"));
                    }
                    error.append("Computer discontinued date is not valid, expected pattern : YYYY-MM-dd");
                    first = false;
                }
                if (!isValidIntervalDate(request.getParameter("introduced"), request.getParameter("discontinued"))) {
                    if (first) {
                        error.append(System.lineSeparator());
                    }
                    error.append("Please select a valid date interval. Discontinued date should be after introduced date.");
                    first = false;
                }
            }
            //If computer company id is filled
            if (!empty(request.getParameter("companyId"))) {
                //Return if discontinued date pattern is valid or not
                if (!isValidCompanyId(request.getParameter("companyId"))) {
                    if (first) {
                        error.append(System.getProperty("line.separator"));
                    }
                    error.append("Company id is not valid.");
                    first = false;
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
        boolean valid;
        //Valid if no company filled
        if (getValidCompanyId(companyId) == null) {
            valid = true;
            //Invalid if companyId parameter is not an Integer
        } else if (getValidCompanyId(companyId) == -1) {
            valid = false;
        } else {
            //Check if company exists in database
            valid = companyDaoIml.findById(Long.valueOf(getValidCompanyId(companyId))).getId() == Long.valueOf(getValidCompanyId(companyId)) ? true : false;
        }
        return valid;
    }

    /**
     * @param introduced   introduced date.
     * @param discontinued discontinued date.
     * @return boolean : interval valid or not.
     */
    public boolean isValidIntervalDate(String introduced, String discontinued) {
        LocalDate intro = input.getLocalDate(introduced);
        LocalDate discon = input.getLocalDate(discontinued);
        return discon.isAfter(intro);
    }

    /**
     * Set valid company id from request.
     *
     * @param company company id.
     * @return company id.
     */
    public Integer getValidCompanyId(String company) {
        Integer companyId = null;
        if (!company.equals("null")) {
            try {
                companyId = Integer.valueOf(company);
            } catch (NumberFormatException e) {
                companyId = -1;
            }
        }
        return companyId;
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

    /**
     * Get errors from ComputerValidator.
     *
     * @return error string from StringBuilder.
     */
    public String getError() {
        return error.toString();
    }


}

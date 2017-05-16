package services.validators.urls;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.daos.ComputerDaoImpl;

/**
 * Created by ebiz on 24/03/17.
 */
public class ComputerEditUrlValidator {

    private final ComputerDaoImpl computerDao;
    public StringBuilder error;

    /**
     * ComputerEditUrlValidator constructor.
     *
     * @param computerDao autowired computerDao
     */
    @Autowired
    public ComputerEditUrlValidator(ComputerDaoImpl computerDao) {
        this.computerDao = computerDao;
    }

    /**
     * @param urlParameters urlParameters.
     */
    public void isUrlValid(String urlParameters) {
        error = new StringBuilder();
        //Check if id parameter is an int
        if (!isValidId(urlParameters)) {
            error.append("Computer id is not valid.");
            //Check if computer exists
        } else if (!computerExists(urlParameters)) {
            error.append("Computer does not exist.");
        }
    }

    /**
     * @param id id.
     * @return boolean : exists or not.
     */
    public boolean computerExists(String id) {
        long computerDbId = computerDao.findById(Long.valueOf(id)).getId();
        long computerFrontId = Long.parseLong(id);
        return (computerDbId == computerFrontId ? true : false);
    }

    /**
     * Check if id from request is an int.
     *
     * @param id id.
     * @return id.
     */
    public boolean isValidId(String id) {
        boolean valid = false;
        try {
            Integer.valueOf(id);
            valid = true;
        } catch (NumberFormatException e) {
        }
        return valid;
    }

    /**
     * Get errors from ComputerEditUrlValidator.
     *
     * @return error string from StringBuilder.
     */
    public String getError() {
        return error.toString();
    }
}

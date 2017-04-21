package services.validators.urls;

import exceptions.validators.urls.ComputerEditUrlValidatorException;
import persistence.daos.ComputerDaoImpl;

/**
 * Created by ebiz on 24/03/17.
 */
public class ComputerEditUrlValidator {

    private static ComputerDaoImpl computerDao;

    /**
     * @param urlParameters urlParameters.
     * @throws ComputerEditUrlValidatorException ComputerEditUrlValidatorException.
     */
    public void isUrlValid(String urlParameters) throws ComputerEditUrlValidatorException {
        //Check if id parameter is an int
        if (!isValidId(urlParameters)) {
            throw new ComputerEditUrlValidatorException("Computer id is not valid.");
            //Check if computer exists
        } else if (!computerExists(urlParameters)) {
            System.out.println("computer id: " + urlParameters);
            throw new ComputerEditUrlValidatorException("Computer does not exist.");
        }
    }

    /**
     * @param id id.
     * @return boolean : exists or not.
     * @throws ComputerEditUrlValidatorException ComputerEditUrlValidatorException.
     */
    public boolean computerExists(String id) throws ComputerEditUrlValidatorException {
        long computerDbId =  computerDao.findById(Long.valueOf(id)).getId();
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

    public void setComputerDao(ComputerDaoImpl computerDao) {
        this.computerDao = computerDao;
    }

    public ComputerDaoImpl getComputerDao() {
        return computerDao;
    }
}

package main.java.daos;

import main.java.exceptions.DAOException;
import main.java.models.Company;
import main.java.models.Computer;

/**
 * Created by ebiz on 14/03/17.
 */
public interface CompanyDao {

    void create( Company company ) throws DAOException;
    Company findById(Long id ) throws DAOException;

}

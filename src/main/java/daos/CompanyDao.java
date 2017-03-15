package main.java.daos;

import main.java.exceptions.DAOException;
import main.java.models.Company;

import java.sql.SQLException;

/**
 * Created by ebiz on 14/03/17.
 */
public interface CompanyDao {

    Company findById(Long id ) throws DAOException;
}

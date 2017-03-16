package main.java.persistence.daos;

import main.java.exceptions.DAOException;
import main.java.models.Company;

import java.util.List;

/**
 * Created by ebiz on 14/03/17.
 */
public interface CompanyDao {

    Company findById(Long id ) throws DAOException;
    List<Company> GetAll() throws DAOException;
    List<Company> getPageList(int page) throws DAOException;
}

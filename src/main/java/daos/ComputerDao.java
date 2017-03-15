package main.java.daos;

import main.java.exceptions.DAOException;
import main.java.models.Computer;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ebiz on 14/03/17.
 */
public interface ComputerDao {

    Long create( Computer computer ) throws DAOException;
    Computer findById(Long id ) throws DAOException;
    Long updateName(Computer computer ) throws DAOException;
    Long Remove(Computer computer ) throws DAOException;
    List<Computer> GetAll() throws DAOException;

}

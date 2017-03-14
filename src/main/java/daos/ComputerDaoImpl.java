package main.java.daos;

import main.java.exceptions.DAOException;
import main.java.models.Computer;
import main.java.services.DAOFactory;

import java.sql.*;

/**
 * Created by ebiz on 14/03/17.
 */
public class ComputerDaoImpl implements ComputerDao {
    private DAOFactory daoFactory;



    public ComputerDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void create(Computer computer) throws DAOException {

    }

    @Override
    public Computer findById(Long id) throws DAOException {
        return null;
    }

    /*
   * Simple méthode utilitaire permettant de faire la correspondance (le
   * mapping) entre une ligne issue de la table des company (un
   * ResultSet) et un bean company.
   */
    private static Computer map( ResultSet resultSet ) throws SQLException {
        Computer computer = new Computer();
        computer.setId( resultSet.getLong( "id" ) );
        computer.setName( resultSet.getString( "name" ) );
        computer.setIntroduced( resultSet.getTimestamp( "introduced" ) );
        computer.setDiscontinued( resultSet.getTimestamp( "discontinued" ) );
        computer.setCompany_id( resultSet.getLong( "company_id" ) );
        return computer;
    }

}

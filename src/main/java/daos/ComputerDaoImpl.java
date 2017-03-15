package main.java.daos;

import main.java.exceptions.DAOException;
import main.java.models.Computer;
import main.java.services.DAOFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static main.java.daos.DAOUtilitaire.fermeturesSilencieuses;
import static main.java.daos.DAOUtilitaire.initialisationRequetePreparee;

/**
 * Created by ebiz on 14/03/17.
 */
public class ComputerDaoImpl implements ComputerDao {
    private DAOFactory daoFactory;

    private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id =? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM computer";

    public ComputerDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public Long create(Computer computer) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        Long id = null;
        System.out.println(computer);
        try {
        /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, computer.getName(), computer.getIntroduced(),computer.getDiscontinued(), computer.getCompany_id() );
            int status = preparedStatement.executeUpdate();
        /* Analyze status returned from insert request */
            if (status == 0) {
                throw new DAOException( "Échec de la création de la company, aucune ligne ajoutée dans la table." );
            }
        /* Get auto-generated id from insert request */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
            /* Initialize id property of computer bean */
                computer.setId( valeursAutoGenerees.getLong(1));
                id = valeursAutoGenerees.getLong(1);
            } else {
                throw new DAOException("Échec de la création de la company en base, aucun ID auto-généré retourné.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
            return id;
        }
    }

    @Override
    public Computer findById(Long id) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Computer computer = null;
        System.out.println("id: "+id);
        try {
         /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
         /* Iterate over returned ResultSet */
            if (resultSet.next()) {
                computer = map(resultSet);
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }
        return computer;
    }

    @Override
    public Long update(Computer computer) throws DAOException{
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        Long id = null;

        try {
         /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE, false, computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getCompany_id(), computer.getId() );
            int status = preparedStatement.executeUpdate();
            /* Analyze status returned from update request */
            if ( status == 0 ) {
                throw new DAOException("Échec de la modification du computer, aucune ligne modifiée dans la table.");
            }else{
                id = computer.getId();
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
            return id;
        }
    }

    @Override
    public Long Remove(Computer computer) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        Long id = null;

        try {
         /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE, false, computer.getId() );
            int status = preparedStatement.executeUpdate();
           /* Analyze status returned from insert request */
            if ( status == 0 ) {
                throw new DAOException("Échec de la suppression du computer, aucune ligne modifiée dans la table.");
            }else{
                id = computer.getId();
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
            return id;
        }
    }

    @Override
    public List<Computer> GetAll() throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Computer computer = null;
        List<Computer> list_computer = new ArrayList<Computer>();
        try {
         /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ALL, false);
            resultSet = preparedStatement.executeQuery();
         /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                computer = map(resultSet);
                list_computer.add(computer);
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }
        return list_computer;
    }

    /*
     * Utilitary method to map one row returned from database and computer bean
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

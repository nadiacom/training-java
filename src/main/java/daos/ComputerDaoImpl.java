package main.java.daos;

import main.java.exceptions.DAOException;
import main.java.models.Computer;
import main.java.services.DAOFactory;

import java.sql.*;
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
    private static final String SQL_UPDATE_NAME = "UPDATE computer SET name = ? WHERE id = ?";
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

        try {
        /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, computer.getName(), computer.getIntroduced(),computer.getDiscontinued(), computer.getCompany_id() );
            int status = preparedStatement.executeUpdate();
        /* Analyse du status retourné par la requête d'insertion */
            if ( status == 0 ) {
                throw new DAOException( "Échec de la création de la company, aucune ligne ajoutée dans la table." );
            }
        /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
                computer.setId( valeursAutoGenerees.getLong(1));
                id = valeursAutoGenerees.getLong(1);
            } else {
                throw new DAOException( "Échec de la création de la company en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
            return id;
        }
    }

    @Override
    public Computer findById(Long id) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Computer computer = null;
        try {
        /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
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
    public Long updateName(Computer computer) throws DAOException{
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        Long id = null;

        try {
        /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE_NAME, false, computer.getName(), computer.getId() );
            int status = preparedStatement.executeUpdate();
            /* Analyse du status retourné par la requête update */
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
        /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE, false, computer.getId() );
            int status = preparedStatement.executeUpdate();
            /* Analyse du status retourné par la requête update */
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
        try {
        /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ALL, false);
            resultSet = preparedStatement.executeQuery();
        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Timestamp introduced = resultSet.getTimestamp("introduced");
                Timestamp discontinued = resultSet.getTimestamp("discontinued");
                long company_id = resultSet.getLong("company_id");
                computer = map(resultSet);
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }
        return (List<Computer>) resultSet;
    }

    /*
   * Simple méthode utilitaire permettant de faire la correspondance (le
   * mapping) entre une ligne issue de la table des computer (un
   * ResultSet) et un bean computer.
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

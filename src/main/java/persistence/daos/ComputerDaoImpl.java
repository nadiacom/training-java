package main.java.persistence.daos;

import main.java.exceptions.DAOException;
import main.java.models.Company;
import main.java.models.Computer;
import main.java.services.cli.CompanyService;
import main.java.persistence.DAOFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static main.java.persistence.daos.DAOUtilitaire.fermeturesSilencieuses;
import static main.java.persistence.daos.DAOUtilitaire.initialisationRequetePreparee;

/**
 * Created by ebiz on 14/03/17.
 */
public class ComputerDaoImpl extends Dao implements ComputerDao {

    private static CompanyService companyService = new CompanyService();

    private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id =? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM computer";
    private static final String SQL_SELECT_BETWEEN = "SELECT * FROM computer WHERE id >= ? AND id < ?";

    private static final int PAGE_SIZE = 10;

    Computer computer = null;

    public ComputerDaoImpl() {
    }

    private static class SingletonHelper{
        private static final ComputerDao INSTANCE = new ComputerDaoImpl();
    }

    public static ComputerDao getInstance(){
        return ComputerDaoImpl.SingletonHelper.INSTANCE;
    }

    @Override
    public Long create(Computer computer) throws DAOException {
        try {
        /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, computer.getName(), computer.getIntroduced(),computer.getDiscontinued(), computer.getCompany().getId() );
            int status = preparedStatement.executeUpdate();
        /* Analyze status returned from insert request */
            if (status == 0) {
                throw new DAOException( "Échec de la création de la company, aucune ligne ajoutée dans la table." );
            }
        /* Get auto-generated id from insert request */
            resultSet = preparedStatement.getGeneratedKeys();
            if ( resultSet.next() ) {
                /* Initialize id property of computer bean */
                computer.setId( resultSet.getLong(1));
                id = resultSet.getLong(1);
            } else {
                throw new DAOException("Échec de la création de la company en base, aucun ID auto-généré retourné.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
            return id;
        }
    }

    @Override
    public Computer findById(Long id) throws DAOException {
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
       try {
         /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE, false, computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getCompany().getId(), computer.getId() );
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
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
            return id;
        }
    }

    @Override
    public Long Remove(Computer computer) throws DAOException {
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
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
            return id;
        }
    }

    @Override
    public List<Computer> GetAll() throws DAOException {
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

    @Override
    public List<Computer> getPageList(int page) throws DAOException {
        List<Computer> list_computer = new ArrayList<Computer>();
        try {
         /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BETWEEN, false, page, page + PAGE_SIZE);
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
        Company company = companyService.getCompanyById(resultSet.getLong("company_id"));
        Computer computer = new
                Computer.ComputerBuilder()
                .id(resultSet.getLong( "id" ))
                .name(resultSet.getString( "name" ))
                .introduced(resultSet.getTimestamp( "introduced" ) != null ? resultSet.getTimestamp( "introduced" ).toLocalDateTime() : null)
                .discontinued(resultSet.getTimestamp( "discontinued" ) != null ? resultSet.getTimestamp( "discontinued" ).toLocalDateTime() : null)
                .company(company)
                .build();
        return computer;
    }
}

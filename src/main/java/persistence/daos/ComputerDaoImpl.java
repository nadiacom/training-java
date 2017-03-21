package persistence.daos;

import cli.CompanyCli;
import exceptions.DAOException;
import models.Company;
import models.Computer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static persistence.daos.DAOUtilitaire.close;
import static persistence.daos.DAOUtilitaire.initialisationRequetePreparee;

/**
 * Created by ebiz on 14/03/17.
 */
public class ComputerDaoImpl extends Dao implements ComputerDao {

    private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id =? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM computer";
    private static final String SQL_COUNT = "SELECT COUNT(*) as total FROM computer";
    private static final String SQL_SELECT_BETWEEN = "SELECT * FROM computer WHERE id >= ? AND id < ?";
    private static final String SQL_SELECT_PAGE = "SELECT * FROM computer AS c LEFT JOIN company ON c.company_id = company.id WHERE c.id LIMIT ? OFFSET ?";
    private static final int PAGE_SIZE = 50;
    private static CompanyCli companyService = new CompanyCli();
    Computer computer = null;

    /**
     * Default constructor.
     */
    private ComputerDaoImpl() {
    }

    private static class SingletonHelper {
        private static final ComputerDao INSTANCE = new ComputerDaoImpl();
    }

    public static ComputerDao getInstance() {
        return ComputerDaoImpl.SingletonHelper.INSTANCE;
    }

    /**
     * Utilitary method to map one row returned from database and computer bean.
     *
     * @param resultSet (required) ResultSet from database request.
     * @return mapped computer.
     * @throws SQLException SQL exception.
     */
    private static Computer map(ResultSet resultSet) throws SQLException {
        Company company = companyService.getCompanyById(resultSet.getLong("company_id"));
        Computer computer = new
                Computer.ComputerBuilder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .introduced(resultSet.getTimestamp("introduced") != null ? resultSet.getTimestamp("introduced").toLocalDateTime().toLocalDate() : null)
                .discontinued(resultSet.getTimestamp("discontinued") != null ? resultSet.getTimestamp("discontinued").toLocalDateTime().toLocalDate() : null)
                .company(company)
                .build();
        return computer;
    }

    @Override
    public Long create(Computer computer) throws DAOException {
        try {
        /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getCompany().getId());
            int status = preparedStatement.executeUpdate();
        /* Analyze status returned from insert request */
            if (status == 0) {
                throw new DAOException("Failed to create company, no row were created.");
            }
        /* Get auto-generated id from insert request */
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                /* Initialize id property of computer bean */
                computer.setId(resultSet.getLong(1));
                id = resultSet.getLong(1);
            } else {
                throw new DAOException("Failed to create company, no auto generated ID returned.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
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
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
        }
        return computer;
    }

    @Override
    public Long update(Computer computer) throws DAOException {
        try {
         /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, false, computer.getName(), computer.getIntroduced().toString(), computer.getDiscontinued().toString(), computer.getCompany().getId(), computer.getId());
            int status = preparedStatement.executeUpdate();
            /* Analyze status returned from update request */
            if (status == 0) {
                throw new DAOException("Failed to update table, no row were modified.");
            } else {
                id = computer.getId();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
            return id;
        }
    }

    @Override
    public Long remove(Computer computer) throws DAOException {
        try {
         /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE, false, computer.getId());
            int status = preparedStatement.executeUpdate();
           /* Analyze status returned from insert request */
            if (status == 0) {
                throw new DAOException("Failed to update table, no row were modified.");
            } else {
                id = computer.getId();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
            return id;
        }
    }

    @Override
    public List<Computer> getAll() throws DAOException {
        List<Computer> listComputer = new ArrayList<Computer>();
        try {
         /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ALL, false);
            resultSet = preparedStatement.executeQuery();
         /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                computer = map(resultSet);
                listComputer.add(computer);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
        }
        return listComputer;
    }

    @Override
    public List<Computer> getPageList(int page) throws DAOException {
        List<Computer> listComputer = new ArrayList<Computer>();
        try {
         /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAGE, false, PAGE_SIZE, (page-1) * PAGE_SIZE );
            resultSet = preparedStatement.executeQuery();
         /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                computer = map(resultSet);
                listComputer.add(computer);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
        }
        return listComputer;
    }

    @Override
    public int getNumberComputers() throws DAOException, SQLException {
        int nb = 1;
        try {
         /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_COUNT, false);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nb = resultSet.getInt("total");
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
        }
        return nb;
    }


}

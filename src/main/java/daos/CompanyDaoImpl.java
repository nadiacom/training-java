package main.java.daos;

import main.java.exceptions.DAOException;
import main.java.models.Company;
import main.java.services.DAOFactory;

import java.sql.*;

import static main.java.daos.DAOUtilitaire.fermeturesSilencieuses;
import static main.java.daos.DAOUtilitaire.initialisationRequetePreparee;

/**
 * Created by ebiz on 14/03/17.
 */
public class CompanyDaoImpl implements CompanyDao {
    private DAOFactory daoFactory;

    private static final String SQL_SELECT_BY_ID = "SELECT id, name FROM company WHERE name = ?";
    private static final String SQL_INSERT = "INSERT INTO company (name) VALUES (?)";

    public CompanyDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void create(Company company) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
        /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, company.getName() );
            int statut = preparedStatement.executeUpdate();
        /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de la company, aucune ligne ajoutée dans la table." );
            }
        /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
                company.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de la création de la company en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    @Override
    public Company findById(Long id) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Company company = null;

        try {
        /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            if (resultSet.next()) {
                company = map(resultSet);
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }
        return company;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des company (un
     * ResultSet) et un bean company.
     */
        private static Company map( ResultSet resultSet ) throws SQLException {
        Company company = new Company();
        company.setId( resultSet.getLong( "id" ) );
        company.setName( resultSet.getString( "name" ) );
        return company;
    }

}

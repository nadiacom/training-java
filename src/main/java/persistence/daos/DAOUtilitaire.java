package persistence.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 * Created by ebiz on 14/03/17.
 */
public class DAOUtilitaire {

    /**
     * Close ResultSet.
     *
     * @param resultSet resultSet.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("Échec de la fermeture du ResultSet : " + e.getMessage());
            }
        }
    }

    /**
     * Close Statement.
     *
     * @param statement prepared statement.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Échec de la fermeture du Statement : " + e.getMessage());
            }
        }
    }

    /**
     * Close Connexion.
     *
     * @param connexion database connexion.
     */
    public static void close(Connection connexion) {
        if (connexion != null) {
            try {
                connexion.close();
            } catch (SQLException e) {
                System.out.println("Échec de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }

    /**
     * Close statement and connexion.
     *
     * @param connexion database connexion.
     * @param statement prepared statement.
     */
    public static void close(Statement statement, Connection connexion) {
        close(statement);
        close(connexion);
    }


    /**
     * Close resultSet, statement and connexion.
     *
     * @param resultSet resultSet.
     * @param connexion database connexion.
     * @param statement prepared statement.
     */
    public static void close(ResultSet resultSet, Statement statement, Connection connexion) {
        close(resultSet);
        close(statement);
        close(connexion);
    }

    /**
     * Initialize prepared statement.
     *
     * @param connexion           database connexion.
     * @param sql                 sql request.
     * @param returnGeneratedKeys boolean: get database generated keys.
     * @param objets              passed argument.
     * @return preparedStatement.
     * @throws SQLException SQL exceptions;
     */
    public static PreparedStatement initialisationRequetePreparee(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i = 0; i < objets.length; i++) {
            preparedStatement.setObject(i + 1, objets[i]);
        }
        System.out.println(preparedStatement);
        return preparedStatement;
    }
}

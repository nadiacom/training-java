package persistence.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ebiz on 14/03/17.
 */
public class DAOUtilitaire {

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
    public static PreparedStatement initPreparedStatement(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i = 0; i < objets.length; i++) {
            preparedStatement.setObject(i + 1, objets[i]);
        }
        return preparedStatement;
    }
}

package main.java.exceptions;

/**
 * Created by ebiz on 14/03/17.
 */
public class DAOConfigurationException extends RuntimeException {

    /*
     * Constructors
     */
    public DAOConfigurationException( String message ) {
        super( message );
    }
    public DAOConfigurationException( String message, Throwable cause ) {
        super( message, cause );
    }
    public DAOConfigurationException( Throwable cause ) {
        super( cause );
    }

}
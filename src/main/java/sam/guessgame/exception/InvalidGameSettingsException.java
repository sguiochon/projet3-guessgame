package sam.guessgame.exception;

/**
 * Exception lancée lorsque l'on tente de lancer une partie avec des paramètres
 * incompatibles ou incorrects.
 */
public class InvalidGameSettingsException extends RuntimeException {

    public InvalidGameSettingsException(String message) {
        super(message);
    }
}

package sam.guessgame.exception;

public class InvalidGameSettingsException extends RuntimeException {

    public InvalidGameSettingsException(String message){
        super(message);
    }
}

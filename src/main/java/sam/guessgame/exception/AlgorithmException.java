package sam.guessgame.exception;

/**
 * Exception lancée si une stratégie d'identification de la solution est mise en défaut.
 * Cela signifie alors, soit que le programme est buggé, soit que l'on a induit l'algorithme en erreur
 * en trichant (en donnant un résultat incorrect en réponse à une tentative)
 */
public class AlgorithmException extends RuntimeException {

    public AlgorithmException(String message) {
        super(message);
    }
}

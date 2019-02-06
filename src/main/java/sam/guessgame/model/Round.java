package sam.guessgame.model;

/**
 * Représentation d'un tour de jeu: contient une combinaison proposée par le joueur dévinant la combinaison
 * secrète, et un résultat produit par le joueur ayant créé la combinaison secrète.
 * @param <T> type de résultat utilisé dans la partie
 */
public class Round<T extends IResult> {

    private Sequence attempt;

    private T result;

    public Round(Sequence sequence) {
        this(sequence, null);
    }

    public Round(Sequence attempt, T result) {
        this.attempt = attempt;
        this.result = result;
    }

    public Sequence getAttempt() {
        return attempt;
    }

    public void setAttempt(Sequence attempt) {
        this.attempt = attempt;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(attempt.toString());
        str.append(" -> ");
        str.append(result == null ? "unknown" : result.toString());
        return str.toString();
    }
}

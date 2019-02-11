package sam.guessgame.model;

/**
 * Résultat rendant compte de la pertinance d'une combinaison soumise durant une partie de Mastermind.
 */
public class MastermindResult implements IResult {

    private int nbCorrectPosition;
    private int nbCorrectSymbol;

    public MastermindResult(int nbCorrectPosition, int nbCorrectSymbol) {
        this.nbCorrectPosition = nbCorrectPosition;
        this.nbCorrectSymbol = nbCorrectSymbol;
    }

    public MastermindResult() {
        this(0, 0);
    }

    @Override
    public int getNbCorrectPosition() {
        return nbCorrectPosition;
    }

    public int getNbCorrectSymbol() {
        return nbCorrectSymbol;
    }

    public void incNbCorrectPosition() {
        nbCorrectPosition++;
    }

    public void incNbCorrectSymbol() {
        nbCorrectSymbol++;
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!( o instanceof MastermindResult))
            return false;

        MastermindResult result = (MastermindResult) o;
        if (this.nbCorrectPosition==result.getNbCorrectPosition() && this.nbCorrectSymbol==result.getNbCorrectSymbol())
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "bien placé: " + nbCorrectPosition + ", bon symbol: " + nbCorrectSymbol;
    }
}

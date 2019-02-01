package sam.guessgame.model;

public class MastermindResult implements IResult{

    private int nbCorrectPosition;
    private int nbCorrectSymbol;

    public MastermindResult(int nbCorrectPosition, int nbCorrectSymbol) {
        this.nbCorrectPosition = nbCorrectPosition;
        this.nbCorrectSymbol = nbCorrectSymbol;
    }

    public MastermindResult() {
        this(0,0);
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
    public String toString(){
        return "bien placé: " + nbCorrectPosition + ", bon symbol: " + nbCorrectSymbol;
    }
}

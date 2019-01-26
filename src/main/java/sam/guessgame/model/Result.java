package sam.guessgame.model;

public class Result {

    private int nbCorrectPosition;
    private int nbCorrectSymbol;

    public Result(int nbCorrectPosition, int nbCorrectSymbol) {
        this.nbCorrectPosition = nbCorrectPosition;
        this.nbCorrectSymbol = nbCorrectSymbol;
    }

    public Result() {
        this(0,0);
    }

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

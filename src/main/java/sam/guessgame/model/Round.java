package sam.guessgame.model;

public class Round {

    private Sequence attempt;

    private Result result;

    public Round(Sequence sequence){
        this(sequence, null);
    }

    public Round(Sequence attempt, Result result) {
        this.attempt = attempt;
        this.result = result;
    }

    public Sequence getAttempt() {
        return attempt;
    }

    public void setAttempt(Sequence attempt) {
        this.attempt = attempt;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(attempt.toString());
        str.append(" -> ");
        str.append(result==null?"unknown":result.toString());
        return str.toString();
    }
}

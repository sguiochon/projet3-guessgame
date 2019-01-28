package sam.guessgame.role;

import sam.guessgame.model.Candidat;
import sam.guessgame.model.Result;
import sam.guessgame.model.Sequence;

public class MastermindHumanCoder extends Initializer implements IMastermindCoder {

    private Sequence winningSequence;

    public MastermindHumanCoder(Candidat candidat) {
        super(candidat);
    }

    @Override
    public Result evaluateAttempt(Sequence attempt) {
        System.out.println("Proposition de l'adversaire: "+ attempt);
        System.out.println("Pour rappel, la combinaison secrète est " + winningSequence);
        return new Result(1,1);
    }

    @Override
    public void initSequence() {
        System.out.println("Indiquez la combinaison secrète (" + candidat.candidatSequence.size() +" symbols parmi " + candidat.candidatSequence.get(0) + "):");
        winningSequence = new Sequence("A", "B", "C", "D");
    }
}

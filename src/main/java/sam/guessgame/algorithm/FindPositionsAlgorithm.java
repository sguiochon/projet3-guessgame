package sam.guessgame.algorithm;

import sam.guessgame.exception.AlgorithmException;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.Result;
import sam.guessgame.model.Sequence;
import sam.guessgame.model.Session;

public class FindPositionsAlgorithm implements VisitorAlgorithm{

    private final Candidat candidat;

    public FindPositionsAlgorithm(Candidat candidat){
        this.candidat = candidat;
    }

    public Sequence visit(Session session){

        int lastRound = session.rounds.size();
        Result lastResult = session.rounds.get(lastRound-1).getResult();
        if (candidat.candidatSequence.size() != (lastResult.getNbCorrectPosition()+lastResult.getNbCorrectSymbol()))
            throw new AlgorithmException("Appel de l'algorithm inapproprié car tous les symbols n'ont pas encore été trouvés.");

        Sequence lastSequence = session.rounds.get(lastRound-1).getAttempt();

        // Reduire le candidat en enlevant les mauvais symbol et en ne gardant que les symbol dans la derniere sequence


        return null;
    }

}

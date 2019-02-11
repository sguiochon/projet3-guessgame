package sam.guessgame.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sam.guessgame.exception.AlgorithmException;
import sam.guessgame.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Component
@Qualifier("knuth")
public class KnuthStrategy implements SessionVisitor<MastermindResult> {

    private final static Random random = new Random(System.currentTimeMillis());

    private final static Logger LOGGER = LoggerFactory.getLogger(KnuthStrategy.class.getName());

    Candidat candidat;
    List<Sequence> candidatSequences = new ArrayList<Sequence>();

    @Override
    public Sequence visit(Session<MastermindResult> session) {

        Round<MastermindResult> lastRound = session.getRounds().get(session.getRounds().size() - 1);
        Sequence attempt = lastRound.getAttempt();
        MastermindResult result = lastRound.getResult();

        removeInvalidCandidatSequences(attempt, result);

        if (candidatSequences.size()==0)
            throw new AlgorithmException("Impossible de trouver une nouvelle sequence. Vous avez donné une réponse erronée (intentionnellement ou pas...)");

        return candidatSequences.get(random.nextInt(candidatSequences.size()));
    }

    private void removeInvalidCandidatSequences(Sequence attempt, MastermindResult result) {
        Iterator<Sequence> iter = candidatSequences.iterator();
        while (iter.hasNext()){
            Sequence sequence = iter.next();
            MastermindResult sequenceResult = evaluateResult(attempt, sequence);
            if (!sequenceResult.equals(result)) {
                iter.remove();
            }
        }
        LOGGER.debug("Nombre de combinaisons candidates: " + candidatSequences.size());
    }

    public MastermindResult evaluateResult(Sequence winningSequence, Sequence sequenceToTest) {
        MastermindResult result = new MastermindResult();
        Sequence winner = winningSequence.duplicate();
        Sequence challenger = sequenceToTest.duplicate();
        // Premier passage pour identifer les bonnes positions:
        Iterator<String> iterChallenger = challenger.getSymbols().iterator();
        int position = 0;
        while (iterChallenger.hasNext()){
            String challengerSymbol = iterChallenger.next();
            String winnerSymbol = winner.getSymbolAt(position);
            if (challengerSymbol.equals(winnerSymbol)){
                // Un symbol trouvé!
                result.incNbCorrectPosition();
                iterChallenger.remove();
                winner.removeSymbolAt(position);
            }
            else{
                position ++;
            }
        }
        // Second passage pour identifier les symbols mal placés
        iterChallenger = challenger.getSymbols().iterator();
        position = 0;
        while(iterChallenger.hasNext()){
            String challengerSymbol = iterChallenger.next();
            for (int i=0; i<winner.getSymbols().size(); i++){
                if (winner.getSymbolAt(i).equals(challengerSymbol)){
                    result.incNbCorrectSymbol();
                    winner.removeSymbolAt(i);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void init(Candidat candidat) {
        this.candidat = candidat;
        generateSequence(new ArrayList<String>(), 0);
        LOGGER.debug("Nombre total de possibilités : " + candidatSequences.size());
    }

    private void generateSequence(ArrayList<String> listOfSymbols, int position) {
        if (position == candidat.candidatSequence.size()) {
            //System.out.println(">>> " + listOfSymbols);
            candidatSequences.add(new Sequence(listOfSymbols));
        } else {
            List<String> possibleSymbols = candidat.candidatSequence.get(position);
            for (String symbol : possibleSymbols) {
                ArrayList extendedListOfSymbols = duplicate(listOfSymbols);
                extendedListOfSymbols.add(symbol);
                generateSequence(extendedListOfSymbols, (position + 1));
            }
        }
    }

    private ArrayList<String> duplicate(ArrayList<String> original) {
        ArrayList<String> copy = new ArrayList<String>();
        for (String element : original) {
            copy.add(element);
        }
        return copy;
    }
}


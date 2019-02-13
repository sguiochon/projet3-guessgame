package sam.guessgame.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.MastermindCandidat;
import sam.guessgame.model.MastermindResult;
import sam.guessgame.model.Sequence;

import java.util.Iterator;

/**
 * Représente un joueur de Mastermind joué par l'ordinateur dont la combinaison secrète doit être trouvée par l'adversaire.
 */
@Component
public class MastermindComputerCoder extends AbstractComputerCoder implements ICoder<MastermindResult> {

    private final static Logger LOGGER = LoggerFactory.getLogger(MastermindComputerCoder.class.getName());

    public MastermindComputerCoder( @Autowired MastermindCandidat mastermindCandidat) {
        super(mastermindCandidat);
    }

    /**
     * Evalue le résultat de la comparaison d'une combinaison avec la combinaison secrète.
     *
     * @param attempt la combinaison à comparer
     * @return résultat de la comparaison.
     */
    @Override
    public MastermindResult evaluateAttempt(Sequence attempt) {
        MastermindResult result = new MastermindResult();
        Sequence winner = winningSequence.duplicate();
        Sequence challenger = attempt.duplicate();

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

    public Sequence getWinningSequence() {
        return winningSequence;
    }

}

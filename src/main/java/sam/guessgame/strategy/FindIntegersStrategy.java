package sam.guessgame.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.model.gameplay.*;

import java.util.Arrays;
import java.util.Random;

/**
 * Stratégie mise en oeuvre par un décodeur joué par l'ordinateur afin de trouver la combinaison secrète
 * au jeu du pluls/moins
 */
public class FindIntegersStrategy implements SessionVisitor<PlusMinusResult> {

    private final static Logger LOGGER = LoggerFactory.getLogger(FindSymbolsStrategy.class.getName());

    private final static Random random = new Random(System.currentTimeMillis());
    private Candidat candidat;


    @Override
    public void init(Candidat candidat) {
        this.candidat = candidat;
    }

    @Override
    public Sequence visit(Session<PlusMinusResult> session) {

        Round<PlusMinusResult> lastRound = session.getRounds().get(session.getRounds().size()-1);
        Sequence attempt = lastRound.getAttempt();
        PlusMinusResult result = lastRound.getResult();

        // Elimination de candidats
        int position = 0;
        for (String symbol : attempt.getSymbols()){
            int intSymbol = Integer.parseInt(symbol);
            PlusMinusResultValue resultValue = result.getResultAt(position);
            switch (resultValue) {
                case Moins:
                    LOGGER.debug("Position " + position + ", le résultat est 'est strictement inférieur'");
                    for (int j=intSymbol; j<=9; j++){
                        candidat.invalidSymbolAt(position, new Symbol(0, String.valueOf(j)), false);
                    }
                    break;
                case Plus:
                    LOGGER.debug("Position " + position + ", le résultat est 'est strictement supérieur'");
                    for (int j=0; j<=intSymbol; j++){
                        candidat.invalidSymbolAt(position, new Symbol(0, String.valueOf(j)), false);
                    }
                    break;
                case Egal:
                    LOGGER.debug("Position " + position + ", le résultat est trouvé");
                    candidat.candidatSequence.set(position, Arrays.asList(symbol));
                    break;
            }
            position++;
        }
        LOGGER.debug("Combinaisons possibles: "+ candidat.toString());
        return candidat.generateRandomSequence(false);
    }


}

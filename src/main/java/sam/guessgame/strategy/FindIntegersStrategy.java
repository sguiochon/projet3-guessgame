package sam.guessgame.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.model.*;

import java.util.Arrays;
import java.util.Random;

public class FindIntegersStrategy implements SessionVisitor<PlusMinusResult> {

    private final static Logger LOGGER = LoggerFactory.getLogger(FindSymbolsStrategy.class.getName());

    private final static Random random = new Random(System.currentTimeMillis());
    private final Candidat candidat;

    public FindIntegersStrategy(Candidat candidat){
        this.candidat = candidat;
    }

    @Override
    public Sequence visit(Session<PlusMinusResult> session) {

        Round<PlusMinusResult> lastRound = session.getRounds().get(session.getRounds().size()-1);
        Sequence attempt = lastRound.getAttempt();
        PlusMinusResult result = lastRound.getResult();

        System.out.println("!!!!! attempt " + attempt);
        System.out.println("!!!!! result " + result);

        // Elimination de candidats
        int position = 0;
        for (String symbol : attempt.getSymbols()){
            int intSymbol = Integer.parseInt(symbol);
            PlusMinusResultValue resultValue = result.getResultAt(position);
            switch (resultValue) {
                case Moins:
                    System.out.println("Moins");
                    for (int j=intSymbol; j<=9; j++){
                        candidat.invalidSymbolAt(position, new Symbol(0, String.valueOf(j)));
                    }
                    break;
                case Plus:
                    for (int j=0; j<=intSymbol; j++){
                        candidat.invalidSymbolAt(position, new Symbol(0, String.valueOf(j)));
                    }
                    break;
                case Egal:
                    candidat.candidatSequence.set(position, Arrays.asList(symbol));
                    break;
            }
            position++;
        }

        System.out.println("new candidat ----> "+ candidat.toString());
        return candidat.generateRandomSequence(false);
    }
}

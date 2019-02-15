package sam.guessgame.model.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.guessgame.model.gameplay.IResult;
import sam.guessgame.model.gameplay.Round;
import sam.guessgame.model.gameplay.Sequence;
import sam.guessgame.model.gameplay.Session;
import sam.guessgame.model.player.ICoder;
import sam.guessgame.model.player.IDecoder;


public class DualPartyGame<T extends ICoder, U extends IDecoder, TT extends ICoder, UU extends IDecoder, V extends IResult> implements IGame {

    private final static Logger LOGGER = LoggerFactory.getLogger(DualPartyGame.class.getName());

    private final int sequenceSize;
    private final int maxNbAttempts;

    public T coder1;
    public U decoder1;
    public Session<V> session1;

    public TT coder2;
    public UU decoder2;
    public Session<V> session2;

    private String description;

    private boolean devMode;

    public DualPartyGame(int sequenceSize, int maxNbAttempts, String description, boolean devMode){
        this.sequenceSize = sequenceSize;
        this.maxNbAttempts = maxNbAttempts;
        this.description = description;
        this.devMode = devMode;
    }

    @Override
    public void init() {
        coder1.initSequence();
        decoder1.initSequence();
        session1 = new Session<V>();
        coder2.initSequence();
        decoder2.initSequence();
        session2 = new Session<V>();
    }

    @Override
    public boolean run() {

        Sequence attempt1;
        Sequence attempt2;
        int nbAttempts=0;
        boolean isSolutionFound1 = false;
        boolean isSolutionFound2 = false;

        do{
            LOGGER.info("----- " + description + " ----- Tour #" + (nbAttempts+1));
            System.out.println("\n" + description + " Tour #" + (nbAttempts+1));

            System.out.println("Partie I: VOUS devez trouver la combinaison secrète de l'ordinateur ---");

            if (devMode)
                System.out.println("Partie I: combinaison secrète: " + coder1.getWinningSequence());

            attempt1 = decoder1.generateAttempt(session1);

            LOGGER.info("Partie I: combinaison proposée: " + attempt1);
            System.out.println("Partie I: Combinaison proposée: " + attempt1);

            IResult result1 = coder1.evaluateAttempt(attempt1);
            Round currentRound1 = new Round(attempt1, result1);
            isSolutionFound1 = coder1.check(result1, sequenceSize);

            System.out.println("Partie I: résultat: " + result1.toString());
            session1.getRounds().add(currentRound1);

            System.out.println("\nPartie II: l'ordinateur doit trouver VOTRE combinaison secrète ---");
            if (devMode)
                System.out.println("Partie II: combinaison secrète: " + coder2.getWinningSequence());

            attempt2 = decoder2.generateAttempt(session2);

            LOGGER.info("Partie II: combinaison proposée: " + attempt2);
            System.out.println("Partie II: Combinaison proposée: " + attempt2);

            IResult result2 = coder2.evaluateAttempt(attempt2);
            Round currentRound2 = new Round(attempt2, result2);
            isSolutionFound2 = coder2.check(result2, sequenceSize);

            System.out.println("Partie II: résultat: " + result2.toString());
            session2.getRounds().add(currentRound2);

            nbAttempts++;
        }
        while(nbAttempts<maxNbAttempts && !isSolutionFound1 && !isSolutionFound2);

        if (isSolutionFound1){
            LOGGER.info("Partie I: Combinaison trouvée par le joueur en " + nbAttempts + " coups");
            System.out.println("\n>>>>> BRAVO! VOUS avez trouvé la combinaison en " + nbAttempts + " coup(s) !!! <<<<<");
        }
        else if (isSolutionFound2){
            LOGGER.info("Partie II: Combinaison trouvée par l'ordinateur en " + nbAttempts + " coups");
            System.out.println("\n>>>>> L'ordinateur a trouvé votre combinaison en " + nbAttempts + " coup(s) !!! <<<<<");
        }
        else{
            LOGGER.debug("Combinaison PAS trouvée par le Decoder");
            System.out.println("\n>>>>> Ni vous ni l'ordinateur n'avez trouvé la combinaison ! <<<<<");
        }

        return isSolutionFound1||isSolutionFound2;
    }
}

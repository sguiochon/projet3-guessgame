package sam.guessgame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sam.guessgame.exception.InvalidGameSettingsException;
import sam.guessgame.model.Candidat;
import sam.guessgame.model.MastermindResult;
import sam.guessgame.model.PlusMinusResult;
import sam.guessgame.role.*;

/**
 * Factory de IGameMode
 */
@Component
public class GameFactory implements IGameFactory{

    private static final Logger LOGGER = LoggerFactory.getLogger(GameFactory.class.getName());

    public final int mastermindSequenceSize;
    public final int plusminusSequenceSize;
    public final int maxNbAttempts;
    public final String[] mastermindPossibleValues;
    public final String[] plusminusPossibleValues;
    private boolean devMode = false;


    Candidat mastermindCandidat1;
    Candidat mastermindCandidat2;

    Candidat plusminusCandidat1;
    Candidat plusminusCandidat2;

    public GameFactory(@Value("${mastermind.sequence.size}") int mastermindSequenceSize,
                       @Value("${mastermind.list.of.symbols}")String[] mastermindPossibleValues,
                       @Value("${plusminus.sequence.size}") int plusminusSequenceSize,
                       @Value("${plusminus.list.of.symbols}")String[] plusminusPossibleValues,
                       @Value("${maximum.number.of.attempts}")int maxNbAttempts,
                       @Value("${devMode:false}") boolean devMode){
        this.mastermindSequenceSize = mastermindSequenceSize;
        this.mastermindPossibleValues = mastermindPossibleValues;
        this.plusminusSequenceSize = plusminusSequenceSize;
        this.plusminusPossibleValues = plusminusPossibleValues;
        this.maxNbAttempts = maxNbAttempts;
        this.devMode = devMode;

        if (mastermindSequenceSize>mastermindPossibleValues.length) {
            LOGGER.error("Paramètres de jeu invalides! Nombre d'emplacements: " + mastermindSequenceSize + ", caractères utilisables " + mastermindPossibleValues);
            throw new InvalidGameSettingsException("Number of possible symbols not allowing unicity of each symbol in the sequence to guess.... Whether add new symbols or reduce the sequence length.");
        }
    }

    /**
     * Retourne une instance de IGameMode sur la base des mode et type de jeu indiqué.
     *
     * @param mode mode de jeu souhaité (ordinateur défend joueur devie, ordinateur devine/joueur a la solution, etc...)
     * @param type type de jeu souhaité (mastermind, jeu du plus/moins)
     * @return l'instance de IGameMode prête à lancer une partie
     */
    public IGameMode getGameMode(GameMode mode, GameType type) {
        switch (type) {
            case MasterMind:
                return getMastermindGameMode(mode, devMode);
            case PlusMoins:
                return getPlusMinusGameMode(mode, devMode);
        }
        return null;
    }

    private IGameMode getMastermindGameMode(GameMode mode, boolean devMode){
        /*
        mastermindCandidat1 = new Candidat(this.mastermindSequenceSize, this.mastermindPossibleValues);
        mastermindCandidat2 = new Candidat(this.mastermindSequenceSize, this.mastermindPossibleValues);
        switch (mode) {
            case Duel:
                //return duelMode;
                DualGameMode<MastermindComputerCoder, MastermindHumanDecoder, MastermindHumanCoder, MastermindComputerDecoder, MastermindResult> dualMode = new DualGameMode<MastermindComputerCoder, MastermindHumanDecoder, MastermindHumanCoder, MastermindComputerDecoder, MastermindResult>(
                        mastermindSequenceSize,
                        maxNbAttempts,
                        "--- ordinateur VS ordinateur ---", devMode);
                dualMode.coder1 = new MastermindComputerCoder(mastermindCandidat1);
                dualMode.decoder1 = new MastermindHumanDecoder(mastermindCandidat1);

                dualMode.coder2 = new MastermindHumanCoder(mastermindCandidat2);
                dualMode.decoder2 = new MastermindComputerDecoder(mastermindCandidat2);
                return dualMode;
            case Challenger:
                SingleGameMode<MastermindComputerCoder, MastermindHumanDecoder, MastermindResult> challengerMode = new SingleGameMode<MastermindComputerCoder, MastermindHumanDecoder, MastermindResult>(
                        mastermindSequenceSize,
                        maxNbAttempts,
                        "--- ordinateur VS ordinateur ---", devMode);
                challengerMode.coder = new MastermindComputerCoder(mastermindCandidat1);
                challengerMode.decoder = new MastermindHumanDecoder(mastermindCandidat1);
                return challengerMode;
            case Defenseur:
                SingleGameMode<MastermindHumanCoder, MastermindComputerDecoder, MastermindResult> defenseurMode = new SingleGameMode<MastermindHumanCoder, MastermindComputerDecoder, MastermindResult>(
                        mastermindSequenceSize,
                        maxNbAttempts,
                        "L'ordinateur doit trouver VOTRE combinaison", devMode);
                defenseurMode.coder = new MastermindHumanCoder(mastermindCandidat1);
                defenseurMode.decoder = new MastermindComputerDecoder(mastermindCandidat1);
                return defenseurMode;
            case Spectateur:
                SingleGameMode<MastermindComputerCoder, MastermindComputerDecoder, MastermindResult> autoMode = new SingleGameMode<MastermindComputerCoder, MastermindComputerDecoder, MastermindResult>(
                        mastermindSequenceSize,
                        maxNbAttempts,
                        "--- ordinateur VS ordinateur ---", devMode);
                autoMode.coder = new MastermindComputerCoder(mastermindCandidat1);
                autoMode.decoder = new MastermindComputerDecoder(mastermindCandidat1);
                return autoMode;
        }
        */
        return null;
    }

    private IGameMode getPlusMinusGameMode(GameMode mode, boolean devMode){
        /*
        plusminusCandidat1 = new Candidat(this.plusminusSequenceSize, this.plusminusPossibleValues);
        plusminusCandidat2 = new Candidat(this.plusminusSequenceSize, this.plusminusPossibleValues);
        switch (mode) {
            case Duel:
                //return duelMode;
                DualGameMode<PlusMinusComputerCoder, PlusMinusHumanDecoder, PlusMinusHumanCoder, PlusMinusComputerDecoder,PlusMinusResult> dualMode =
                        new DualGameMode<PlusMinusComputerCoder, PlusMinusHumanDecoder, PlusMinusHumanCoder, PlusMinusComputerDecoder, PlusMinusResult>(
                        plusminusSequenceSize,
                        maxNbAttempts,
                        "--- ordinateur VS ordinateur ---", devMode);
                dualMode.coder1 = new PlusMinusComputerCoder(plusminusCandidat1);
                dualMode.decoder1 = new PlusMinusHumanDecoder(plusminusCandidat1);
                dualMode.coder2 = new PlusMinusHumanCoder(plusminusCandidat1);
                dualMode.decoder2 = new PlusMinusComputerDecoder(plusminusCandidat1);
                return dualMode;

            case Challenger:
                SingleGameMode<PlusMinusComputerCoder, PlusMinusHumanDecoder, PlusMinusResult> challengerMode =
                        new SingleGameMode<PlusMinusComputerCoder, PlusMinusHumanDecoder, PlusMinusResult>(
                            plusminusSequenceSize,
                            maxNbAttempts,
                        "--- VOUS devez trouver la combinaison de l'ordinateur ---", devMode);
                challengerMode.coder = new PlusMinusComputerCoder(plusminusCandidat1);
                challengerMode.decoder = new PlusMinusHumanDecoder(plusminusCandidat1);
                return challengerMode;
            case Defenseur:
                SingleGameMode<PlusMinusHumanCoder, PlusMinusComputerDecoder, PlusMinusResult> defenseurMode =
                        new SingleGameMode<PlusMinusHumanCoder, PlusMinusComputerDecoder, PlusMinusResult>(
                                plusminusSequenceSize,
                                maxNbAttempts,
                        "L'ordinateur doit trouver VOTRE combinaison", devMode);
                defenseurMode.coder = new PlusMinusHumanCoder(plusminusCandidat1);
                defenseurMode.decoder = new PlusMinusComputerDecoder(plusminusCandidat1);
                return defenseurMode;
            case Spectateur:
                SingleGameMode<PlusMinusComputerCoder, PlusMinusComputerDecoder, PlusMinusResult> autoMode =
                        new SingleGameMode<PlusMinusComputerCoder, PlusMinusComputerDecoder, PlusMinusResult>(
                        plusminusSequenceSize,
                        maxNbAttempts,
                        "--- ordinateur VS ordinateur ---", devMode);
                autoMode.coder = new PlusMinusComputerCoder(plusminusCandidat1);
                autoMode.decoder = new PlusMinusComputerDecoder(plusminusCandidat1);
                return autoMode;

        }
        */
        return null;
    }



}

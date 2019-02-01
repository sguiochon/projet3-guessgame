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

@Component
public class GameModeFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameModeFactory.class.getName());

    public final int mastermindSequenceSize;
    public final int plusminusSequenceSize;
    public final int maxNbAttempts;
    public final String[] mastermindPossibleValues;
    public final String[] plusminusPossibleValues;


    Candidat mastermindCandidat;

    Candidat plusminusCandidat;

    public GameModeFactory(@Value("${mastermind.sequence.size}") int mastermindSequenceSize,
                           @Value("${mastermind.list.of.symbols}")String[] mastermindPossibleValues,
                           @Value("${plusminus.sequence.size}") int plusminusSequenceSize,
                           @Value("${plusminus.list.of.symbols}")String[] plusminusPossibleValues,
                           @Value("${maximum.number.of.attempts}")int maxNbAttempts){
        this.mastermindSequenceSize = mastermindSequenceSize;
        this.mastermindPossibleValues = mastermindPossibleValues;
        this.plusminusSequenceSize = plusminusSequenceSize;
        this.plusminusPossibleValues = plusminusPossibleValues;
        this.maxNbAttempts = maxNbAttempts;

        if (mastermindSequenceSize>mastermindPossibleValues.length) {
            LOGGER.error("Invalid game settings: " + mastermindSequenceSize + ", " + mastermindPossibleValues);
            throw new InvalidGameSettingsException("Number of possible symbols not allowing unicity of each symbol in the sequence to guess.... Whether add new symbols or reduce the sequence length.");
        }
    }

    public IGameMode getGameMode(GameMode mode, GameType type) {
        switch (type) {
            case MasterMind:
                //return duelMode;
                return getMastermindGameMode(mode);
            case PlusMoins:
                return getPlusMinusGameMode(mode);
        }
        return null;
    }

    private IGameMode getMastermindGameMode(GameMode mode){
        mastermindCandidat = new Candidat(this.mastermindSequenceSize, this.mastermindPossibleValues);
        switch (mode) {
            case Duel:
                //return duelMode;
                SingleGameMode<MastermindComputerCoder, MastermindComputerDecoder, MastermindResult> testMode = new SingleGameMode<MastermindComputerCoder, MastermindComputerDecoder, MastermindResult>(
                        mastermindSequenceSize,
                        maxNbAttempts,
                        "--- ordinateur VS ordinateur ---");
                testMode.coder = new MastermindComputerCoder(mastermindCandidat);
                testMode.decoder = new MastermindComputerDecoder(mastermindCandidat);
                return testMode;
            case Challenger:
                SingleGameMode<MastermindComputerCoder, MastermindHumanDecoder, MastermindResult> challengerMode = new SingleGameMode<MastermindComputerCoder, MastermindHumanDecoder, MastermindResult>(
                        mastermindSequenceSize,
                        maxNbAttempts,
                        "--- ordinateur VS ordinateur ---");
                challengerMode.coder = new MastermindComputerCoder(mastermindCandidat);
                challengerMode.decoder = new MastermindHumanDecoder(mastermindCandidat);
                return challengerMode;
            case Defenseur:
                SingleGameMode<MastermindHumanCoder, MastermindComputerDecoder, MastermindResult> defenseurMode = new SingleGameMode<MastermindHumanCoder, MastermindComputerDecoder, MastermindResult>(
                        mastermindSequenceSize,
                        maxNbAttempts,
                        "L'ordinateur doit trouver VOTRE combinaison");
                defenseurMode.coder = new MastermindHumanCoder(mastermindCandidat);
                defenseurMode.decoder = new MastermindComputerDecoder(mastermindCandidat);
                return defenseurMode;
            case Spectateur:
                SingleGameMode<MastermindComputerCoder, MastermindComputerDecoder, MastermindResult> autoMode = new SingleGameMode<MastermindComputerCoder, MastermindComputerDecoder, MastermindResult>(
                        mastermindSequenceSize,
                        maxNbAttempts,
                        "--- ordinateur VS ordinateur ---");
                autoMode.coder = new MastermindComputerCoder(mastermindCandidat);
                autoMode.decoder = new MastermindComputerDecoder(mastermindCandidat);
                return autoMode;
        }
        return null;
    }

    private IGameMode getPlusMinusGameMode(GameMode mode){
        plusminusCandidat = new Candidat(this.plusminusSequenceSize, this.plusminusPossibleValues);
        switch (mode) {
            case Duel:
                //return duelMode;
                SingleGameMode<PlusMinusComputerCoder, PlusMinusHumanDecoder, PlusMinusResult> testMode =
                        new SingleGameMode<PlusMinusComputerCoder, PlusMinusHumanDecoder, PlusMinusResult>(
                        plusminusSequenceSize,
                        maxNbAttempts,
                        "--- ordinateur VS ordinateur ---");
                testMode.coder = new PlusMinusComputerCoder(plusminusCandidat);
                testMode.decoder = new PlusMinusHumanDecoder(plusminusCandidat);
                return testMode;

            case Challenger:
                SingleGameMode<PlusMinusComputerCoder, PlusMinusHumanDecoder, PlusMinusResult> challengerMode =
                        new SingleGameMode<PlusMinusComputerCoder, PlusMinusHumanDecoder, PlusMinusResult>(
                            plusminusSequenceSize,
                            maxNbAttempts,
                        "--- VOUS devez trouver la combinaison de l'ordinateur ---");
                challengerMode.coder = new PlusMinusComputerCoder(plusminusCandidat);
                challengerMode.decoder = new PlusMinusHumanDecoder(plusminusCandidat);
                return challengerMode;
            case Defenseur:
                SingleGameMode<PlusMinusHumanCoder, PlusMinusComputerDecoder, PlusMinusResult> defenseurMode =
                        new SingleGameMode<PlusMinusHumanCoder, PlusMinusComputerDecoder, PlusMinusResult>(
                                plusminusSequenceSize,
                                maxNbAttempts,
                        "L'ordinateur doit trouver VOTRE combinaison");
                defenseurMode.coder = new PlusMinusHumanCoder(plusminusCandidat);
                defenseurMode.decoder = new PlusMinusComputerDecoder(plusminusCandidat);
                return defenseurMode;
            case Spectateur:
                SingleGameMode<PlusMinusComputerCoder, PlusMinusComputerDecoder, PlusMinusResult> autoMode =
                        new SingleGameMode<PlusMinusComputerCoder, PlusMinusComputerDecoder, PlusMinusResult>(
                        plusminusSequenceSize,
                        maxNbAttempts,
                        "--- ordinateur VS ordinateur ---");
                autoMode.coder = new PlusMinusComputerCoder(plusminusCandidat);
                autoMode.decoder = new PlusMinusComputerDecoder(plusminusCandidat);
                return autoMode;

        }
        return null;
    }



}

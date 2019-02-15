package sam.guessgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sam.guessgame.exception.InvalidGameSettingsException;
import sam.guessgame.model.game.*;
import sam.guessgame.model.gameplay.MastermindResult;
import sam.guessgame.model.gameplay.PlusMinusResult;
import sam.guessgame.model.player.*;

/**
 * Factory de {@link sam.guessgame.model.game.IGame}.
 */
@Component
public class GameFactory implements IGameFactory {

    public final int mastermindSequenceSize;
    public final int plusminusSequenceSize;
    public final int maxNbAttempts;
    public final String[] mastermindPossibleValues;
    public final String[] plusminusPossibleValues;
    @Autowired
    MastermindComputerDecoder mastermindComputerDecoder;
    @Autowired
    MastermindComputerCoder mastermindComputerCoder;
    @Autowired
    MastermindHumanDecoder mastermindHumanDecoder;
    @Autowired
    MastermindHumanCoder mastermindHumanCoder;
    @Autowired
    PlusMinusComputerDecoder plusminusComputerDecoder;
    @Autowired
    PlusMinusComputerCoder plusminusComputerCoder;
    @Autowired
    PlusMinusHumanDecoder plusminusHumanDecoder;
    @Autowired
    PlusMinusHumanCoder plusminusHumanCoder;
    private boolean devMode = false;

    public GameFactory(@Value("${mastermind.sequence.size}") int mastermindSequenceSize,
                       @Value("${mastermind.list.of.symbols}") String[] mastermindPossibleValues,
                       @Value("${plusminus.sequence.size}") int plusminusSequenceSize,
                       @Value("${plusminus.list.of.symbols}") String[] plusminusPossibleValues,
                       @Value("${maximum.number.of.attempts}") int maxNbAttempts,
                       @Value("${devMode:false}") boolean devMode) {
        this.mastermindSequenceSize = mastermindSequenceSize;
        this.mastermindPossibleValues = mastermindPossibleValues;
        this.plusminusSequenceSize = plusminusSequenceSize;
        this.plusminusPossibleValues = plusminusPossibleValues;
        this.maxNbAttempts = maxNbAttempts;
        this.devMode = devMode;

        if (mastermindSequenceSize > mastermindPossibleValues.length) {
            throw new InvalidGameSettingsException("Mastermind: number of possible symbols not allowing unicity of each symbol in the sequence to guess.... Whether add new symbols or reduce the sequence length.");
        }

        if (plusminusSequenceSize > plusminusPossibleValues.length) {
            throw new InvalidGameSettingsException("PlusMoins: number of possible symbols not allowing unicity of each symbol in the sequence to guess.... Whether add new symbols or reduce the sequence length.");
        }
    }

    /**
     * Retourne une instance de IGameMode sur la base des mode et type de jeu indiqué.
     *
     * @param mode mode de jeu souhaité (ordinateur défend joueur devie, ordinateur devine/joueur a la solution, etc...)
     * @param type type de jeu souhaité (mastermind, jeu du plus/moins)
     * @return l'instance de IGameMode prête à lancer une partie
     */
    public IGame getGameMode(GameMode mode, GameType type) {
        switch (type) {
            case MasterMind:
                return getMastermindGameMode(mode, devMode);
            case PlusMoins:
                return getPlusMinusGameMode(mode, devMode);
        }
        return null;
    }

    private IGame getPlusMinusGameMode(GameMode mode, boolean devMode) {
        switch (mode) {
            case Duel:
                //return duelMode;
                DualPartyGame<PlusMinusComputerCoder, PlusMinusHumanDecoder, PlusMinusHumanCoder, PlusMinusComputerDecoder, PlusMinusResult> dualMode = new DualPartyGame<>(plusminusSequenceSize, maxNbAttempts, "--- Duel entre l'ordinateur et VOUS ---", devMode);
                dualMode.coder1 = plusminusComputerCoder;
                dualMode.decoder1 = plusminusHumanDecoder;
                dualMode.coder2 = plusminusHumanCoder;
                dualMode.decoder2 = plusminusComputerDecoder;
                return dualMode;

            case Challenger:
                SinglePartyGame<PlusMinusComputerCoder, PlusMinusHumanDecoder, PlusMinusResult> challengerMode = new SinglePartyGame<>(plusminusSequenceSize, maxNbAttempts, "--- VOUS devez trouver la combinaison de l'ordinateur ---", devMode);
                challengerMode.coder = plusminusComputerCoder;
                challengerMode.decoder = plusminusHumanDecoder;
                return challengerMode;
            case Defenseur:
                SinglePartyGame<PlusMinusHumanCoder, PlusMinusComputerDecoder, PlusMinusResult> defenseurMode = new SinglePartyGame<>(plusminusSequenceSize, maxNbAttempts, "--- L'ordinateur doit trouver VOTRE combinaison ---", devMode);
                defenseurMode.coder = plusminusHumanCoder;
                defenseurMode.decoder = plusminusComputerDecoder;
                return defenseurMode;
            case Spectateur:
                SinglePartyGame<PlusMinusComputerCoder, PlusMinusComputerDecoder, PlusMinusResult> autoMode = new SinglePartyGame<>(plusminusSequenceSize, maxNbAttempts, "--- ordinateur VS ordinateur ---", devMode);
                autoMode.coder = plusminusComputerCoder;
                autoMode.decoder = plusminusComputerDecoder;
                return autoMode;
        }
        return null;
    }

    private IGame getMastermindGameMode(GameMode mode, boolean devMode) {
        switch (mode) {
            case Duel:
                //return duelMode;
                DualPartyGame<MastermindComputerCoder, MastermindHumanDecoder, MastermindHumanCoder, MastermindComputerDecoder, MastermindResult> dualMode = new DualPartyGame<>(mastermindSequenceSize, maxNbAttempts, "--- Duel entre l'ordinateur et VOUS ---", devMode);
                dualMode.coder1 = mastermindComputerCoder;
                dualMode.decoder1 = mastermindHumanDecoder;
                dualMode.coder2 = mastermindHumanCoder;
                dualMode.decoder2 = mastermindComputerDecoder;
                return dualMode;
            case Challenger:
                SinglePartyGame<MastermindComputerCoder, MastermindHumanDecoder, MastermindResult> challengerMode = new SinglePartyGame<>(mastermindSequenceSize, maxNbAttempts, "--- VOUS devez trouver la combinaison de l'ordinateur ---", devMode);
                challengerMode.coder = mastermindComputerCoder;
                challengerMode.decoder = mastermindHumanDecoder;
                return challengerMode;
            case Defenseur:
                SinglePartyGame<MastermindHumanCoder, MastermindComputerDecoder, MastermindResult> defenseurMode = new SinglePartyGame<>(mastermindSequenceSize, maxNbAttempts, "L'ordinateur doit trouver VOTRE combinaison", devMode);
                defenseurMode.coder = mastermindHumanCoder;
                defenseurMode.decoder = mastermindComputerDecoder;
                return defenseurMode;
            case Spectateur:
                SinglePartyGame<MastermindComputerCoder, MastermindComputerDecoder, MastermindResult> autoMode = new SinglePartyGame<>(mastermindSequenceSize, maxNbAttempts, "--- ordinateur VS ordinateur ---", devMode);
                autoMode.coder = mastermindComputerCoder;
                autoMode.decoder = mastermindComputerDecoder;
                return autoMode;
        }
        return null;
    }

}

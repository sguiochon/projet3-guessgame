package sam.guessgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sam.guessgame.exception.InvalidGameSettingsException;
import sam.guessgame.model.*;
import sam.guessgame.role.IMastermindCoder;
import sam.guessgame.role.IMastermindDecoder;
import sam.guessgame.role.MastermindComputerCoder;
import sam.guessgame.role.MastermindComputerDecoder;

public abstract class SingleGameMode implements IGameMode {

    public final int sequenceSize;
    public final int maxNbAttempts;
    public final String[] possibleValues;

    IMastermindCoder coder;
    IMastermindDecoder decoder;

    public Session session;

    public SingleGameMode(int sequenceSize, String[] possibleValues, int maxNbAttempts){
        this.sequenceSize = sequenceSize;
        this.possibleValues = possibleValues;
        this.maxNbAttempts = maxNbAttempts;
        if (sequenceSize>possibleValues.length)
            throw new InvalidGameSettingsException("Number of possible symbols not allowing unicity of each symbol in the sequence to guess.... Whether add new symbols or reduce the sequence length.");

        session = new Session();
    }

    @Override
    public void run(GameType gameType) {
        init(gameType);

        Sequence attempt;
        int nbAttempts=0;
        boolean isSolutionFound = false;
        do{
            System.out.println("--------------------------------------");
            System.out.println("Tour #" + (nbAttempts+1));

            // Decoder makes an attempt:
            attempt = decoder.generateAttempt(session);

            // Coder returns a result
            Result result = coder.evaluateAttempt(attempt);
            //System.out.println("Coder, réponse: " + result.toString());

            isSolutionFound = check(result);

            Round currentRound = new Round(attempt, result);
            System.out.println(currentRound.toString());
            session.rounds.add(currentRound);
            nbAttempts++;
        }
        while(nbAttempts<maxNbAttempts && !isSolutionFound);
        if (isSolutionFound){
            System.out.println(">>>>> BRAVO!!! <<<<<");
        }
        //return isSolutionFound;
    }

    private boolean check(Result result) {
        //System.out.println("Size: " + sequenceSize + ", nb de symbols trouvés: " + result.getNbCorrectPosition());
        if (result.getNbCorrectPosition()==sequenceSize)
            return true;
        else
            return false;
    }
/*
    private void init(GameType gameType){
        switch(gameType) {
            case PlusMoins:
                System.out.println();
                break;
            case MasterMind:
                System.out.println();
                break;
        }
    }
    */
}

package sam.guessgame;

import org.springframework.beans.factory.annotation.Autowired;
import sam.guessgame.role.MastermindGame;

public abstract class SingleGameMode implements IGameMode {

    //@Autowired
    //MastermindGame mastermindGame;

    @Override
    public void run(GameType gameType) {
        init(gameType);

        //
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

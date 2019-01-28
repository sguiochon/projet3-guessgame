package sam.guessgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameModeFactory {

    @Autowired
    ChallengerMode challengerMode;

    @Autowired
    DefenseurMode defenseurMode;

    @Autowired
    AutomaticMode automaticMode;

    public IGameMode getGameMode(GameMode mode){
        switch (mode){
            case Duel:
                return null;
            case Challenger:
                return challengerMode;
            case Defenseur:
                return defenseurMode;
            case Spectateur:
                return automaticMode;
        }
        return null;
    }


}

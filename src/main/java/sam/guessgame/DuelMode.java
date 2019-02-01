package sam.guessgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class DuelMode implements IGameMode {

    //@Autowired
    //ChallengerMode challengerMode;

    //@Autowired
    //DefenseurMode defenseurMode;

    @Override
    public void init() {
        //defenseurMode.init();
        //.init();
    }

    @Override
    public boolean run() {



        return true;
    }
}

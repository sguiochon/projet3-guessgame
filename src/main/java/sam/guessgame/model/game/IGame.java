package sam.guessgame.model.game;

public interface IGame {

    /**
     * Initialisation d'une partie
     */
    void init();

    /**
     * Lancement d'une partie
     * @return
     */
    boolean run();

}

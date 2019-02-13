package sam.guessgame;

public interface IGameMode {

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

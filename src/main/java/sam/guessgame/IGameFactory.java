package sam.guessgame;

/**
 * Factory de jeux
 */
public interface IGameFactory {

    /**
     * Retourne une instance de jeu correspondant aux type et mode de jeu
     * @param gameMode mode de jeu
     * @param gameType type de jeu
     * @return l'instance de jeu correspondant
     */
    IGameMode getGameMode(GameMode gameMode, GameType gameType);

}

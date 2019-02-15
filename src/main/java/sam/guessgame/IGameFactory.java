package sam.guessgame;

import sam.guessgame.model.game.GameMode;
import sam.guessgame.model.game.GameType;
import sam.guessgame.model.game.IGame;

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
    IGame getGameMode(GameMode gameMode, GameType gameType);

}

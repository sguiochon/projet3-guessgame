package sam.guessgame;

public interface IGameFactory {

    IGameMode getGameMode(GameMode gameMode, GameType gameType);

}

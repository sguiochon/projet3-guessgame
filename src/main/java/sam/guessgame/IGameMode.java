package sam.guessgame;

public interface IGameMode {

    void init(GameType gameType);
    void run(GameType gameType);
    String getDescription();

}

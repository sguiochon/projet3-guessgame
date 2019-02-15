package sam.guessgame.model.player;

/**
 * Comportement consistant à initialiser une combinaison en début d'une partie. Ce comportement
 * est implémenté à la fois par le {@link ICoder} et le {@link IDecoder}.
 */
public interface ISequenceInitializer {

    public void initSequence();

}


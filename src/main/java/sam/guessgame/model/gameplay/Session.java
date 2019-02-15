package sam.guessgame.model.gameplay;

import sam.guessgame.strategy.SessionVisitor;

import java.util.*;

/**
 * Représente l'ensemble des tours d'une partie: chaque tour joué est collecté par la classe
 * Session.
 * @param <T> type de résultat de chaque tour.
 */
public class Session<T extends IResult> implements Visitable{

    private List<Round<T>> rounds = new ArrayList<Round<T>>();

    public List<Round<T>> getRounds() {
        return rounds;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        int roundNb = 1;
        for (Round round : rounds){
            str.append(roundNb).append(": ");
            str.append(round.toString()).append("\n");
            roundNb++;
        }
        return str.toString();
    }

    @Override
    public Sequence accept(SessionVisitor visitor) {
        return visitor.visit(this);
    }
}

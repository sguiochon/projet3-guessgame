package sam.guessgame.model;

import sam.guessgame.algorithm.VisitorAlgorithm;

import java.util.*;

public class Session implements Visitable{

    public List<Round> rounds = new ArrayList<Round>();

    //public void addRound(Round round){
    //    rounds.add(round);
    //}

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
    public Sequence accept(VisitorAlgorithm visitor) {
        return visitor.visit(this);
    }
}

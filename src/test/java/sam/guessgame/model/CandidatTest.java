package sam.guessgame.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CandidatTest {

    @Test
    public void givenACandidat_WhenFoundSymbol_ThenCandidatProperlyUpdated(){
        Candidat candidat = new Candidat(3, new String[]{"A", "B", "C", "D", "E"});

        Symbol symbol = new Symbol(2, "B");

        candidat.foundSymbol(symbol);
        //System.out.println(candidat.toString());
        Assert.assertTrue("(A,C,D,E)-(A,C,D,E)-(B)".equals(candidat.toString()));
    }

    @Test
    public void givenACandidat_WhenInvalidSymbol_TheCandidatProperlyUpdated(){
        Candidat candidat = new Candidat(3, new String[]{"A", "B", "C", "D", "E"});

        Symbol symbol = new Symbol(2, "B");
        candidat.invalidSymbol(symbol);
        //System.out.println("Invalid " +candidat.toString());
        Assert.assertTrue("(A,C,D,E)-(A,C,D,E)-(A,C,D,E)".equals(candidat.toString()));
    }

    @Test
    public void givenACandidat_WhenFoundAllSymbols_ThenCandidatProperlyUpdated(){

        Candidat candidat = new Candidat(new ArrayList<String>(Arrays.asList(new String[]{"D"})),
                new ArrayList<String>(Arrays.asList(new String[]{"B", "C", "E", "F"})),
                new ArrayList<String>(Arrays.asList(new String[]{"A"})),
                new ArrayList<String>(Arrays.asList(new String[]{"B","C","E","F"})));

        candidat.validSymbols(new Sequence("A", "D", "B", "C"));

        System.out.println(candidat.toString());
        Assert.assertTrue("Reduction de candidat quand on connait tous les symbols", "(D)-(B,C)-(A)-(B,C)".equals(candidat.toString()));
    }

    @Test
    public void givenACandidat_WhenGeneratingRandomSequence_ThenSequenceIsValid(){
        Candidat candidat = new Candidat(
                new ArrayList<String>(Arrays.asList(new String[]{"D"})),
                new ArrayList<String>(Arrays.asList(new String[]{"B"})),
                new ArrayList<String>(Arrays.asList(new String[]{"A"})),
                new ArrayList<String>(Arrays.asList(new String[]{"C"})));
        Assert.assertTrue("D B A C".equals(candidat.generateRandomSequence().toString()));

    }
}

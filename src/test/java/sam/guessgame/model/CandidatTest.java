package sam.guessgame.model;

import org.junit.Assert;
import org.junit.Test;
import sam.guessgame.strategy.FindSymbolsStrategyCandidat;

import java.util.ArrayList;
import java.util.Arrays;

public class CandidatTest {

    @Test
    public void givenACandidat_WhenFoundSymbol_ThenCandidatProperlyUpdated(){
        // Arrange
        Candidat candidat = new Candidat(3, new String[]{"A", "B", "C", "D", "E"});
        Symbol symbol = new Symbol(2, "B");
        // Act
        candidat.foundSymbol(symbol);
        // Assert
        Assert.assertTrue("(A,C,D,E)-(A,C,D,E)-(B)".equals(candidat.toString()));
    }

    @Test
    public void givenACandidat_WhenInvalidSymbol_TheCandidatProperlyUpdated(){
        // Arrange
        FindSymbolsStrategyCandidat candidat = new FindSymbolsStrategyCandidat(new Candidat(3, new String[]{"A", "B", "C", "D", "E"}));
        Symbol symbol = new Symbol(2, "B");
        // Act
        candidat.invalidSymbol(symbol);
        // Assert
        Assert.assertTrue("(A,C,D,E)-(A,C,D,E)-(A,C,D,E)".equals(candidat.toString()));
    }

    @Test
    public void givenACandidat_WhenGeneratingRandomSequence_ThenSequenceIsValid(){
        // Arrange
        Candidat candidat = new Candidat(4, new String[]{""});
        candidat.candidatSequence.set(0, new ArrayList<String>(Arrays.asList(new String[]{"D"})));
        candidat.candidatSequence.set(1, new ArrayList<String>(Arrays.asList(new String[]{"B"})));
        candidat.candidatSequence.set(2, new ArrayList<String>(Arrays.asList(new String[]{"A"})));
        candidat.candidatSequence.set(3, new ArrayList<String>(Arrays.asList(new String[]{"C"})));

        // Act & Assert
        Assert.assertTrue("D B A C".equals(candidat.generateRandomSequence(true).toString()));
    }

    @Test
    public void givenACandidat_WhenGeneratingRandomSequence_ThenSequenceIsValid2(){
        // Arrange
        Candidat candidat = new Candidat(4, new String[]{""});
        candidat.candidatSequence.set(0, new ArrayList<String>(Arrays.asList(new String[]{"1", "2", "3","4"})));
        candidat.candidatSequence.set(1, new ArrayList<String>(Arrays.asList(new String[]{"0"})));
        candidat.candidatSequence.set(2, new ArrayList<String>(Arrays.asList(new String[]{"2"})));
        candidat.candidatSequence.set(3, new ArrayList<String>(Arrays.asList(new String[]{"7", "8", "9"})));

        System.out.println(candidat.generateRandomSequence(false).toString());
    }

    @Test
    public void givenACandidat_WhenGeneratingRandomSequence_TheProcessingOrderIsAdapted(){
        Candidat candidat = new Candidat(4, new String[]{""});
        candidat.candidatSequence.set(0, new ArrayList<String>(Arrays.asList(new String[]{"D", "F", "G","H"})));
        candidat.candidatSequence.set(1, new ArrayList<String>(Arrays.asList(new String[]{"D","F","G","H"})));
        candidat.candidatSequence.set(2, new ArrayList<String>(Arrays.asList(new String[]{"D","H"})));
        candidat.candidatSequence.set(3, new ArrayList<String>(Arrays.asList(new String[]{"D", "H"})));

        System.out.println(candidat.generateRandomSequence(true).toString());
    }

}

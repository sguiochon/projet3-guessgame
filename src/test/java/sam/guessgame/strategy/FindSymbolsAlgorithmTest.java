package sam.guessgame.strategy;

import org.junit.Assert;
import org.junit.Test;
import sam.guessgame.model.gameplay.*;

import java.util.ArrayList;
import java.util.Arrays;

public class FindSymbolsAlgorithmTest {

    @Test
    public void givenASequence_WhenReplacingOneSymbol_ThenNewSequenceIsValid(){

        MastermindCandidat candidat = new MastermindCandidat(4, new String[]{""});
        candidat.candidatSequence.set(0, new ArrayList<String>(Arrays.asList(new String[]{"D"})));
        candidat.candidatSequence.set(1, new ArrayList<String>(Arrays.asList(new String[]{"B", "C", "E", "F"})));
        candidat.candidatSequence.set(2, new ArrayList<String>(Arrays.asList(new String[]{"A"})));
        candidat.candidatSequence.set(3, new ArrayList<String>(Arrays.asList(new String[]{"B","C","E","F"})));

        FindSymbolsStrategy algo = new FindSymbolsStrategy();
        algo.init(candidat);

        Sequence sequence = new Sequence(new String[]{"D", "B", "A", "F"});
        System.out.println("Sequence: " + sequence.toString());
        Sequence newSequence = algo.changeOneSymbol(sequence, null);
        System.out.println("New sequence: " + newSequence.toString());
        //Assert.assertTrue(newSequence.toString().contains("*"));
        Assert.assertTrue(newSequence.getSymbols().size()==4);
        Assert.assertTrue("D C A F".equals(newSequence.toString()));

        // Ici, on fournit un historique afin de vérifier que la nouvelle sequence n'est pas dedans
        Session<MastermindResult> session = new Session();
        Round round = new Round(new Sequence("D", "C", "A", "F"));
        session.getRounds().add(round);newSequence = algo.changeOneSymbol(sequence, session);
        System.out.println("New sequence using history: " + newSequence.toString());
        Assert.assertTrue(!"D C A F".equals(newSequence.toString()));

    }

    @Test
    public void fix(){
        MastermindCandidat candidat = new MastermindCandidat(4, new String[]{});
        candidat.candidatSequence.set(0, new ArrayList<String>(Arrays.asList(new String[]{"D"})));
        candidat.candidatSequence.set(1, new ArrayList<String>(Arrays.asList(new String[]{"C"})));
        candidat.candidatSequence.set(2, new ArrayList<String>(Arrays.asList(new String[]{"A", "B", "H"})));
        candidat.candidatSequence.set(3, new ArrayList<String>(Arrays.asList(new String[]{"A", "B", "H"})));

        Session session  = new Session();
        session.getRounds().add(new Round(new Sequence(new String[]{"D","E","C","B"}), new MastermindResult(1,1)));
        session.getRounds().add(new Round(new Sequence(new String[]{"A","E","C","B"}), new MastermindResult(0,2)));
        session.getRounds().add(new Round(new Sequence(new String[]{"A","F","C","B"}), new MastermindResult(0,2)));
        session.getRounds().add(new Round(new Sequence(new String[]{"A","G","C","B"}), new MastermindResult(0,2)));
        session.getRounds().add(new Round(new Sequence(new String[]{"A","H","C","B"}), new MastermindResult(0,3)));
        session.getRounds().add(new Round(new Sequence(new String[]{"A","H","E","B"}), new MastermindResult(0,2)));
        session.getRounds().add(new Round(new Sequence(new String[]{"A","C","E","B"}), new MastermindResult(1,1)));
        session.getRounds().add(new Round(new Sequence(new String[]{"A","C","H","B"}), new MastermindResult(1,2)));

        FindSymbolsStrategy algo = new FindSymbolsStrategy();
        algo.init(candidat);
        Sequence sequence = algo.changeOneSymbol(new Sequence(new String[]{"A","C","H","B"}), session);

        Assert.assertTrue("D C A B".equals(sequence.toString()));
    }
}

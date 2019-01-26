package sam.guessgame.algorithm;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import sam.guessgame.model.*;

import java.util.ArrayList;
import java.util.Arrays;

public class FindSymbolsAlgorithmTest {
/*
    @Test
    public void givenASession_WhenSearchingTheBestAttempt_ThenResponseIsCorrect(){
        Session session = new Session();

        Round round1 = new Round(new Sequence(new String[]{"A", "B", "C"}), new Result(0, 2));
        Round round2 = new Round(new Sequence(new String[]{"C", "B", "D"}), new Result(0, 3));
        Round round3 = new Round(new Sequence(new String[]{"B", "C", "A"}), new Result(1, 1));

        session.rounds.add(round1);
        session.rounds.add(round2);
        session.rounds.add(round3);

        FindAllSymbolsWhateverTheirPosition algo = new FindAllSymbolsWhateverTheirPosition(new Candidat(3, new String[]{"A", "B", "C", "D"}));

        Sequence foundBestAttempt = algo.findBestMatchingAttempt(session);

        Assert.assertTrue(foundBestAttempt.toString().trim().equals("C B D"));

    }
*/

    @Test
    @Ignore
    public void givenASequence_WhenReplacingOneSymbol_ThenNewSequenceIsValid(){

        Candidat candidat = new Candidat(new ArrayList<String>(Arrays.asList(new String[]{"D"})),
                new ArrayList<String>(Arrays.asList(new String[]{"B", "C", "E", "F"})),
                new ArrayList<String>(Arrays.asList(new String[]{"A"})),
                new ArrayList<String>(Arrays.asList(new String[]{"B","C","E","F"})));

        FindSymbolsAlgorithm algo = new FindSymbolsAlgorithm(candidat);

        Sequence sequence = new Sequence(new String[]{"D", "B", "A", "F"});
        System.out.println("Sequence: " + sequence.toString());
        Sequence newSequence = algo.replaceOneSymbolByCandidateInSequence(sequence, null).get();
        System.out.println("New sequence: " + newSequence.toString());
        //Assert.assertTrue(newSequence.toString().contains("*"));
        Assert.assertTrue(newSequence.getSymbols().size()==4);
        Assert.assertTrue("D C A F".equals(newSequence.toString()));

        // Ici, on fournit un historique afin de vérifier que la nouvelle sequence n'est pas dedans
        Session session = new Session();
        Round round = new Round(new Sequence("D", "C", "A", "F"));
        session.rounds.add(round);newSequence = algo.replaceOneSymbolByCandidateInSequence(sequence, session).get();
        System.out.println("New sequence using history: " + newSequence.toString());
        Assert.assertTrue(!"D C A F".equals(newSequence.toString()));

    }

    @Test
    public void fix(){
        Candidat candidat = new Candidat(
                new ArrayList<String>(Arrays.asList(new String[]{"D"})),
                new ArrayList<String>(Arrays.asList(new String[]{"C"})),
                new ArrayList<String>(Arrays.asList(new String[]{"A", "B", "H"})),
                new ArrayList<String>(Arrays.asList(new String[]{"A","B","H"})));

        Session session  = new Session();
        session.rounds.add(new Round(new Sequence(new String[]{"D","E","C","B"}), new Result(1,1)));
        session.rounds.add(new Round(new Sequence(new String[]{"A","E","C","B"}), new Result(0,2)));
        session.rounds.add(new Round(new Sequence(new String[]{"A","F","C","B"}), new Result(0,2)));
        session.rounds.add(new Round(new Sequence(new String[]{"A","G","C","B"}), new Result(0,2)));
        session.rounds.add(new Round(new Sequence(new String[]{"A","H","C","B"}), new Result(0,3)));
        session.rounds.add(new Round(new Sequence(new String[]{"A","H","E","B"}), new Result(0,2)));
        session.rounds.add(new Round(new Sequence(new String[]{"A","C","E","B"}), new Result(1,1)));
        session.rounds.add(new Round(new Sequence(new String[]{"A","C","H","B"}), new Result(1,2)));

        FindSymbolsAlgorithm algo = new FindSymbolsAlgorithm(candidat);
//        algo.replaceOneSymbolByCandidateInSequence(new Sequence(new String[]{"A","C","H","B"}), session).toString();
        System.out.println(algo.replaceOneSymbolByCandidateInSequence(new Sequence(new String[]{"A","C","H","B"}), session).toString());

    }
}

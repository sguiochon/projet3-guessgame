package sam.guessgame.strategy;

import org.junit.Test;
import sam.guessgame.model.gameplay.MastermindCandidat;
import sam.guessgame.model.gameplay.MastermindResult;
import sam.guessgame.model.gameplay.Sequence;

public class KnuthStrategyTest {


    @Test
    public void verifierCreationDeToutesLesPossibilitesEnDebutDeJeu() {
        KnuthStrategy knuth = new KnuthStrategy();
        MastermindCandidat candidat = new MastermindCandidat(4, new String[]{"A", "B", "C", "D", "E", "F", "G", "H"});
        knuth.init(candidat);

        Sequence a = new Sequence("F", "F", "F", "F");
        Sequence b = new Sequence("C", "F", "F", "D");
        MastermindResult r1 = knuth.evaluateResult(b,a);
        MastermindResult r2 = knuth.evaluateResult(a,b);

        System.out.println(r1);
        System.out.println(r2);

    }
}

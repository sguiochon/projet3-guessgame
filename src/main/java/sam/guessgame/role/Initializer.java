package sam.guessgame.role;

import sam.guessgame.model.Candidat;
import sam.guessgame.model.Sequence;

import java.util.Random;

public abstract class Initializer implements IInitializer{

    //protected int sequenceSize;
    //protected String[] possibleValues;
    private final static Random random = new Random(System.currentTimeMillis());;
    protected Candidat candidat;
    protected Sequence startingSequence;

    public Initializer(Sequence sequence){
        this.startingSequence = sequence;
        init();
    }

    public Initializer(Candidat candidat){
        this.candidat = candidat;
        init();
    }
    /*
    public SequenceGenerator(int sequenceSize, String[] possibleValues){
        this.sequenceSize = sequenceSize;
        this.possibleValues = possibleValues;
        init();
    }*/
/*
    public Sequence generateRandomSequence(){
        Sequence sequence = new Sequence();
        Candidat workCandidat = candidat.duplicate();

        int currentColumn = 0;
        for (List<String> possiblesValues : workCandidat.candidatSequence){
            int r = random.nextInt(possiblesValues.size());
            String selectedSymbol = possiblesValues.get(r);
            sequence.addSymbol(selectedSymbol);
            for (int j=currentColumn+1; j<workCandidat.candidatSequence.size(); j++){
                workCandidat.candidatSequence.get(j).remove(selectedSymbol);
            }
        }

        //System.out.println("-> " + sequence.toString());
        return sequence;
    }
    */
}

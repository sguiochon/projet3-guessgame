package sam.guessgame.model;

import java.util.ArrayList;
import java.util.List;

public class PlusMinusResult implements IResult {

    private int nbCorrectPosition;
    public List<PlusMinusResultValue> results = new ArrayList<>();

    @Override
    public int getNbCorrectPosition() {
        return nbCorrectPosition;
    }

    public void setNbCorrectPosition(int nb){
        nbCorrectPosition = nb;
    }

    public void setResultAt(int position, PlusMinusResultValue result){
        if (results.size()<=position){
            int firstUnsetPosition = results.size();
            for (int i = firstUnsetPosition ; i<=position ; i++){
                results.add(PlusMinusResultValue.Inconnu);
            }
        }
        results.set(position, result);
    }

    public PlusMinusResultValue getResultAt(int position){
        return results.get(position);
    }

    public String toString(){
        StringBuffer str = new StringBuffer();
        for (PlusMinusResultValue value : results){
            str.append(value.toString());
        }
        return str.toString();
    }
}

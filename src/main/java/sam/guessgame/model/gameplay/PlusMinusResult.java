package sam.guessgame.model.gameplay;

import java.util.ArrayList;
import java.util.List;

/**
 * Résultat rendant compte de la pertinance d'une combinaison soumise durant une partie de PlusMoins.
 */
public class PlusMinusResult implements IResult {

    public List<PlusMinusResultValue> results = new ArrayList<>();
    private int nbCorrectPosition;

    @Override
    public int getNbCorrectPosition() {
        return nbCorrectPosition;
    }

    public void setNbCorrectPosition(int nb) {
        nbCorrectPosition = nb;
    }

    /**
     * Affecte un résultat unitaire (+,-,=) à la position spécifiée.
     *
     * @param position la position de la combinaison dont on veut affecter le résultat
     * @param result   le résultat à affecter
     */
    public void setResultAt(int position, PlusMinusResultValue result) {
        if (results.size() <= position) {
            int firstUnsetPosition = results.size();
            for (int i = firstUnsetPosition; i <= position; i++) {
                results.add(PlusMinusResultValue.Inconnu);
            }
        }
        results.set(position, result);
    }

    /**
     * Retourne le résultat unitaire (+,-,=) pour la position indiquée.
     *
     * @param position position dont on retourne la valeur du résultat.
     * @return le résultat à la position indiquée
     */
    public PlusMinusResultValue getResultAt(int position) {
        return results.get(position);
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        for (PlusMinusResultValue value : results) {
            str.append(value.toString());
        }
        return str.toString();
    }
}

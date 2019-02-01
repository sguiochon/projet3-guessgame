package sam.guessgame;

import org.springframework.stereotype.Component;
import sam.guessgame.model.Sequence;

import java.util.List;
import java.util.Scanner;

@Component
public class InputScanner {

    public int inputIntegerFromArray(String message, int... authorizedValues) {
        boolean isValidInput = false;
        int intInput = -1;
        do {
            intInput = scanInt(message);
            if (intInput != -1) {
                for (int val : authorizedValues) {
                    if (val == intInput) {
                        isValidInput = true;
                        break;
                    }
                }
                if (!isValidInput)
                    System.out.println("Le nombre saisi ne fait pas partie des valeurs autorisées...");
            }
        }
        while (!isValidInput);
        return intInput;
    }

    public int inputIntegerFromMinMax(String message, int min, int max) {
        boolean isValidInput = false;
        int intInput = -1;
        do {
            intInput = scanInt(message);
            if (intInput != -1) {
                if (intInput >= min && intInput <= max)
                    isValidInput = true;
                else
                    System.out.println("Le nombre doit être compris entre " + min + " et " + max);
            }
        }
        while (!isValidInput);
        return intInput;
    }

    public Sequence inputSequence(String message, int sequenceSize, List<String> authorizedValues, boolean uniqueSymbolsOnly) {
        Scanner scanner = new Scanner(System.in);
        String listOfSymbols = authorizedValues.stream().reduce("", (a, b) -> a + " " + b);
        String input;
        boolean isValidInput = false;
        do {
            System.out.println(message);
            System.out.println("(Choisissez " + sequenceSize + " symbols parmi la liste suivante: " + listOfSymbols + ")");
            input = scanner.next();
            scanner.nextLine();

            if (input.length() == sequenceSize) {
                isValidInput = true;
                for (int i = 0; i < input.length(); i++) {
                    boolean testSymbolInList = listOfSymbols.contains(String.valueOf(input.charAt(i)));
                    if (!testSymbolInList) {
                        System.out.println("Les symbols en dehors de la liste ne sont pas autorisés");
                        isValidInput = false;
                        break;
                    }
                    if (uniqueSymbolsOnly) {
                        if (input.lastIndexOf(input.charAt(i)) != input.indexOf(input.charAt(i))) {
                            System.out.println("Pas de doublon autorisé...");
                            isValidInput = false;
                            break;
                        }
                    }
                }
            } else {
                System.out.println("Respectez la longueur autorisée...");
            }
        }
        while (!isValidInput);
        Sequence sequence = new Sequence(input.split(""));
        return sequence;
    }

    /*
    public static void main(String[] args) {
        InputScanner input = new InputScanner();
        input.inputIntegerFromMinMax("Saisir un nombre entier entre 0 et 1: ", 0,1);
        input.inputIntegerFromArray("Saisir un nombre entier entre 1 et 4: ", 1,2,3,4);
    }
    */


    private int scanInt(String message) {
        Scanner scanner = new Scanner(System.in);
        int intInput = -1;

        System.out.println(message);
        String input = scanner.next();
        scanner.nextLine();
        try {
            intInput = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("La valeur saisie n'est pas un nombre entier...");
        }
        return intInput;
    }
}

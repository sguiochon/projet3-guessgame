package sam.guessgame;

import sam.guessgame.model.gameplay.Sequence;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Classe utilitaire assurant la saisie des données durant l'exécution de l'application.
 * Les méthodes dont elle dispose sont ainsi appelées par plusieurs classes...
 */
public class InputScanner {

    /**
     * Assure la saisie d'un nombre entier faisant partie d'une liste donnée. L'utilisateur est invité
     * à recommencer la saisie en cas d'entrée incorrecte, jusqu'à correction.
     * @param message: message affiché avant la saisie
     * @param authorizedValues: liste des valeurs admises à la saisie
     * @return la valeur saisie
     */
    public static int inputIntegerFromArray(String message, int... authorizedValues) {
        boolean isValidInput = false;
        int intInput = -1;
        do {
            Optional<Integer> result = scanInt(message);
            if (result.isPresent()) {
                intInput = result.get();
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

    /**
     * Assure la saisie d'un nombre entier compris entre deux valeurs données. L'utilisateur est invité
     * à recommencer la saisie en cas d'entrée incorrecte, jusqu'à correction.
     * @param message message affiché avant la saisie
     * @param min valeur minimale admise
     * @param max valeur maximale admise
     * @return la valeur saisie
     */
    public static int inputIntegerFromMinMax(String message, int min, int max) {
        boolean isValidInput = false;
        int intInput = -1;
        do {
            Optional<Integer> result = scanInt(message);
            if (result.isPresent()) {
                intInput = result.get();
                if (intInput >= min && intInput <= max)
                    isValidInput = true;
                else
                    System.out.println("Le nombre doit être compris entre " + min + " et " + max);
            }
        }
        while (!isValidInput);
        return intInput;
    }

    /**
     * Assure la saisie d'une combinaison respectant le nombre de symbols donné, la liste de symbols donnée, et le fait que chaque symbol dans
     * la combinaison doit être unique ou non. L'utilisateur est invité à corriger sa saisie jusqu'à ce qu'elle soit valide, si nécessaire.
     * @param message message affiché avant la saisie
     * @param sequenceSize nombre exact de caractères à saisir
     * @param authorizedValues symbols admis dans la combinaison
     * @param uniqueSymbolsOnly vrai si la séquence saisie ne doit pas comporter de doublons
     * @return l'instance de type Sequence contenant la combinaison saisie par l'utilisateur
     */
    public static Sequence inputSequence(String message, int sequenceSize, List<String> authorizedValues, boolean uniqueSymbolsOnly) {
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

    /**
     * Traite la saisie afin de la convertir en nombre entier. En cas d'échec, la méthode retourne un Optional vide.
     * @param message: message affiché sur la console avant saisie
     * @return
     */
    private static Optional<Integer> scanInt(String message) {
        Scanner scanner = new Scanner(System.in);
        int intInput = -1;

        System.out.println(message);
        String input = scanner.next();
        scanner.nextLine();
        try {
            intInput = Integer.parseInt(input);
            return Optional.of(Integer.valueOf(intInput));
        } catch (NumberFormatException e) {
            System.out.println("La valeur saisie n'est pas un nombre entier...");
            return Optional.empty();
        }
    }
}

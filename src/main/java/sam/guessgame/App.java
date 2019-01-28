package sam.guessgame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import sam.guessgame.role.MastermindGame;

import java.util.Scanner;

@Configuration
@ComponentScan(basePackages = {"sam.guessgame", "sam.guessgame.role"})
@PropertySource("classpath:config.properties")
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class.getName());

    @Autowired
    GameModeFactory factory;

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);

        App app = ctx.getBean(App.class);
        app.run();

    }

    private void run(){

        GameMode gameMode = null;
        GameType gameType = null;

        System.out.println("Sélectionnez le type de jeu");
        gameType = GameType.getByInternalValue(inputFromKeyboard(GameType.getDescription(), GameType.getInternalValues()));

        System.out.println("Sélectionnez le mode de jeu");
        gameMode = GameMode.getByInternalValue(inputFromKeyboard(GameMode.getDescription(), GameMode.getInternalValues()));

        LOGGER.debug("Type de jeu: " + gameType, gameType);
        LOGGER.debug("Mode de jeu: " + gameMode, gameMode);
        
        IGameMode game = factory.getGameMode(gameMode);

        game.init(gameType);
        game.run(gameType);

    }

    private int inputFromKeyboard(String message, int... authorizedValues){
        Scanner scanner = new Scanner(System.in);
        boolean isValidInput = false;
        int intInput = 0;
        do {
            System.out.println(message);
            String input = scanner.next();
            scanner.nextLine();
            try{
                intInput = Integer.parseInt(input);
            }
            catch(NumberFormatException e){
                System.out.println("Valeur saisie non valide...");
            }
            for (int val : authorizedValues){
                if (val==intInput)
                    isValidInput = true;
            }
        }
        while(!isValidInput);
        return intInput;
    }
}

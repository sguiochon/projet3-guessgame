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
@ComponentScan(basePackages = {"sam.guessgame.role"})
@PropertySource("classpath:config.properties")
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class.getName());

    @Autowired
    MastermindGame mastermindGame;

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);

        App app = ctx.getBean(App.class);
        app.run();

    }

    private void run(){

        Scanner scanner = new Scanner(System.in);
        int gameMode = 0;
        int gameType = 0;
        boolean isValidInput = false;

        do{
            System.out.println("Choisissez le type de jeu: 1 (MasterMind) ou 2 (Plus/Moins)");
            String input = scanner.next();
            scanner.nextLine();
            try{
                gameType = Integer.parseInt(input);
            }
            catch(NumberFormatException e){
                System.out.println("Valeur saisie non valide...");
            }
            if (gameType==1 || gameType==2)
                isValidInput = true;
        }
        while(!isValidInput);

        do {
            System.out.println("Choisissez le mode de jeu: 1 (Challenger) ou 2 (Défenseur) ou 3 (Duel)");
            String input = scanner.next();
            scanner.nextLine();
            try{
                gameMode = Integer.parseInt(input);
            }
            catch(NumberFormatException e){
                System.out.println("Valeur saisie non valide...");
            }
            if (gameMode>=1 && gameMode<=3)
                isValidInput = true;
        }
        while(!isValidInput);

    }
}

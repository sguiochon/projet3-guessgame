package sam.guessgame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Classe constituant le point d'entrée de l'application Guess Game
 */
@Configuration
@ComponentScan(basePackages = {"sam.guessgame", "sam.guessgame.role", "sam.guessgame.model", "sam.guessgame.strategy"})
@PropertySource("classpath:config.properties")
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class.getName());

    @Autowired
    @Qualifier("experimental")
    IGameFactory factory;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
        App app = ctx.getBean(App.class);
        app.run();
    }

    private void run() {
        do {
            GameMode gameMode = null;
            GameType gameType = null;

            System.out.println("Quel type de jeu choisissez-vous?");
            gameType = GameType.getByInternalValue(InputScanner.inputIntegerFromArray(GameType.getDescription(), GameType.getInternalValues()));

            System.out.println("Quel mode de jeu choisissez-vous?");
            gameMode = GameMode.getByInternalValue(InputScanner.inputIntegerFromArray(GameMode.getDescriptions(), GameMode.getInternalValues()));

            LOGGER.info("Type de jeu: " + gameType);
            LOGGER.info("Mode de jeu: " + gameMode);

            IGameMode game = factory.getGameMode(gameMode, gameType);

            game.init();
            game.run();
        } while (!(0 == InputScanner.inputIntegerFromArray("Tapez 0 pour quitter ou 1 pour lancer une autre partie", 0, 1)));

    }
}

package sam.guessgame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"sam.guessgame", "sam.guessgame.role", "sam.guessgame.model"})
@PropertySource("classpath:config.properties")
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class.getName());

    @Autowired
    GameModeFactory factory;

    @Autowired
    InputScanner inputScanner;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
        App app = ctx.getBean(App.class);
        app.run();
    }

    private void run() {

        GameMode gameMode = null;
        GameType gameType = null;

        System.out.println("Quel type de jeu choisissez-vous?");
        gameType = GameType.getByInternalValue(inputScanner.inputIntegerFromArray(GameType.getDescription(), GameType.getInternalValues()));

        System.out.println("Quel mode de jeu choisissez-vous?");
        gameMode = GameMode.getByInternalValue(inputScanner.inputIntegerFromArray(GameMode.getDescriptions(), GameMode.getInternalValues()));

        LOGGER.debug("Type de jeu: " + gameType, gameType);
        LOGGER.debug("Mode de jeu: " + gameMode, gameMode);

        IGameMode game = factory.getGameMode(gameMode, gameType);

        game.init();
        game.run();

    }

}

package sam.guessgame.strategy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import sam.guessgame.model.MastermindResult;

/**
 * Configuration Spring permettant d'injecter la stratégie de l'auteur ({@link FindSymbolsStrategy}) dans le jeu de Mastermind.
 */
@Configuration
@Profile("sam")
public class SamConfig {

    @Bean
    SessionVisitor<MastermindResult> strategy(){
        return new FindSymbolsStrategy();
    }
}

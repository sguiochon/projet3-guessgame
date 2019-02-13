package sam.guessgame.strategy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import sam.guessgame.model.MastermindResult;

/**
 * Configuration Spring permettant d'injecter la stratégie de Knuth ({@link KnuthStrategy}) dans le jeu du Mastermin.
 */
@Configuration
@Profile({"knuth", "default"})
public class KnuthConfig {

    @Bean
    SessionVisitor<MastermindResult> strategy() {
        return new KnuthStrategy();
    }
}

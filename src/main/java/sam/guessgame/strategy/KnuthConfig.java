package sam.guessgame.strategy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import sam.guessgame.model.MastermindResult;

@Configuration
@Profile({"knuth", "default"})
public class KnuthConfig {

    @Bean
    SessionVisitor<MastermindResult> strategy() {
        return new KnuthStrategy();
    }
}

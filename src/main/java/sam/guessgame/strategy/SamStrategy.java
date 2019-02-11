package sam.guessgame.strategy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import sam.guessgame.model.MastermindResult;

@Configuration
@Profile("sam")
public class SamStrategy {

    @Bean
    SessionVisitor<MastermindResult> strategy(){
        return new FindSymbolsStrategy();
    }
}

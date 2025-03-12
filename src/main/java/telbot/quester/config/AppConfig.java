package telbot.quester.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import telbot.quester.questguts.QuestRepository;

@Configuration
public class AppConfig {

    @Bean
    public QuestRepository questRepository(){
        return new QuestRepository();
    }
}

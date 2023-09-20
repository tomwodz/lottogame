package pl.tomwodz.lottogame.domain.resultannouncer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tomwodz.lottogame.domain.resultchecker.ResultCheckerFacade;

import java.time.Clock;

@Configuration
public class ResultAnnouncerConfiguration {

    @Bean
    public ResultAnnouncerFacade resultAnnouncerFacade (ResultRepository resultRepository, ResultCheckerFacade checkerFacade, Clock clock){
        ResultResponseFactory resultResponseFactory = new ResultResponseFactory();
        return new ResultAnnouncerFacade(clock, resultRepository, checkerFacade, resultResponseFactory);
    }
}

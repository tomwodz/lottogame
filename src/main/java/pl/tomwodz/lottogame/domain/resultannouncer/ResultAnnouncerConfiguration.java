package pl.tomwodz.lottogame.domain.resultannouncer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tomwodz.lottogame.domain.resultchecker.ResultCheckerFacade;

import java.time.Clock;
import java.util.Optional;

@Configuration
public class ResultAnnouncerConfiguration {

    @Bean
    ResultRepository resultRepository(){
        return new ResultRepository() {
            @Override
            public ResultResponse save(ResultResponse resultResponse) {
                return null;
            }

            @Override
            public boolean existsById(String hash) {
                return false;
            }

            @Override
            public Optional<ResultResponse> findById(String hash) {
                return Optional.empty();
            }
        };
    }

    @Bean
    public ResultAnnouncerFacade resultAnnouncerFacade (ResultRepository resultRepository, ResultCheckerFacade checkerFacade, Clock clock){
        ResultResponseFactory resultResponseFactory = new ResultResponseFactory();
        return new ResultAnnouncerFacade(clock, resultRepository, checkerFacade, resultResponseFactory);
    }
}

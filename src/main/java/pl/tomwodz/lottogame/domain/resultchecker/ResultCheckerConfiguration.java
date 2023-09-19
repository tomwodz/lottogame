package pl.tomwodz.lottogame.domain.resultchecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tomwodz.lottogame.domain.numbergenerator.NumberGeneratorFacade;
import pl.tomwodz.lottogame.domain.numberreceiver.NumberReceiverFacade;

import java.util.List;
import java.util.Optional;

@Configuration
 class ResultCheckerConfiguration {

    @Bean
    PlayerRepository playerRepository(){
        return new PlayerRepository() {
            @Override
            public List<Player> saveAll(List<Player> players) {
                return null;
            }

            @Override
            public Optional<Player> findById(String hash) {
                return Optional.empty();
            }
        };
    }

    @Bean
    public ResultCheckerFacade resultCheckerFacade(NumberReceiverFacade numberReceiverFacade,
                                            NumberGeneratorFacade numberGeneratorFacade,
                                            PlayerRepository playerRepository){
        ResultFactory resultFactory = new ResultFactory();
        WinnerGenerator winnerGenerator = new WinnerGenerator();
        return new ResultCheckerFacade(numberReceiverFacade, numberGeneratorFacade,
                        playerRepository, winnerGenerator, resultFactory);
    }
}

package pl.tomwodz.lottogame.infrastructure.clock;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ClockConfiguration {

    @Bean
    @Primary
    Clock clock() {
        return Clock.systemUTC();
    }

}

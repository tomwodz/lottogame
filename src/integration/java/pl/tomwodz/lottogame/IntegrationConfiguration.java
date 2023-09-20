package pl.tomwodz.lottogame;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.tomwodz.lottogame.domain.AdjustableClock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@Configuration
@Profile("integration")
public class IntegrationConfiguration {
    @Bean
    AdjustableClock clock(){
        return AdjustableClock.ofLocalDateAndLocalTime(
                LocalDate.of(2022,11,16),
                LocalTime.of(10, 0),
                ZoneId.systemDefault());
    }
}

package pl.tomwodz.lottogame.domain.drawdategenerator;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DrawDateGeneratorFacadeTest {

    @Test
    void shouldReturnCorrectDrawDate(){

        //given
        Clock clock =  Clock.fixed(LocalDateTime.of(2023, 9, 13, 10, 0, 0)
                .toInstant(ZoneOffset.UTC), ZoneId.of("Europe/London"));
        DrawDateGeneratorFacade drawDateGeneratorFacade = new DrawDateGeneratorFacade(clock);

        //when
        LocalDateTime testDrawDate = drawDateGeneratorFacade.getNextDrawDate();

        // then
        LocalDateTime expectDrawDate = LocalDateTime.of(2023, 9, 16, 12, 0, 0);
        assertThat(testDrawDate).isEqualTo(expectDrawDate);
    }

    @Test
    void shouldReturnNextSaturdayWhenDateIsSaturdayNoon(){

        //given
        Clock clock =  Clock.fixed(LocalDateTime.of(2023, 9, 16, 12, 0, 0)
                .toInstant(ZoneOffset.UTC), ZoneId.of("Europe/London"));
        DrawDateGeneratorFacade drawDateGeneratorFacade = new DrawDateGeneratorFacade(clock);

        //when
        LocalDateTime testDrawDate = drawDateGeneratorFacade.getNextDrawDate();

        // then
        LocalDateTime expectDrawDate = LocalDateTime.of(2023, 9, 23, 12, 0, 0);
        assertThat(testDrawDate).isEqualTo(expectDrawDate);
    }

    @Test
    void shouldReturnNextSaturdayWhenDateIsSaturdayAfterNoon(){

        //given
        Clock clock =  Clock.fixed(LocalDateTime.of(2023, 9, 16, 16, 0, 0)
                .toInstant(ZoneOffset.UTC), ZoneId.of("Europe/London"));
        DrawDateGeneratorFacade drawDateGeneratorFacade = new DrawDateGeneratorFacade(clock);

        //when
        LocalDateTime testDrawDate = drawDateGeneratorFacade.getNextDrawDate();

        // then
        LocalDateTime expectDrawDate = LocalDateTime.of(2023, 9, 23, 12, 0, 0);
        assertThat(testDrawDate).isEqualTo(expectDrawDate);
    }
}

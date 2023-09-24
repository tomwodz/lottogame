package pl.tomwodz.lottogame.domain.numberreceiver;

import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import pl.tomwodz.lottogame.domain.AdjustableClock;
import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.NumberReceiverResponseDto;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.TicketDto;
import pl.tomwodz.lottogame.domain.validator.ValidatorConfiguration;
import pl.tomwodz.lottogame.domain.validator.ValidatorFacade;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class NumberReceiverFacadeTest {

    CriteriaForGenerateNumbersConfigurationProperties criteria = CriteriaForGenerateNumbersConfigurationProperties
            .builder()
            .lowerBand(1)
            .upperBand(99)
            .count(6)
            .build();

    AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2023,9,7, 14, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
    ValidatorFacade validatorFacade = new ValidatorConfiguration().validatorFacade(criteria);

    NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().numberReceiverFacade(
            new TicketRepositoryTestImpl(),
            clock,
            new HashGeneratorImpl(),
            validatorFacade,
            new DrawDateGeneratorFacade(clock)
    );

    @Test
    public void shouldReturnSuccessWhenUserGaveSixNumbers(){

        //given
        Set<Integer> numbersFromUser = Set.of(1,2,3,4,5,6);

        //when
        NumberReceiverResponseDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("Success");

    }

    @Test
    public void shouldReturnFailedWhenUserGaveAtLeastOneNumberOutOfRangeOf1To99(){

        //given
        Set<Integer> numbersFromUser = Set.of(1000,2,3,4,5,6);

        //when
        NumberReceiverResponseDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("Failed");
    }

    @Test
    public void shouldReturnFailedWhenUserGaveLessThanSixNumbers(){

        //given
        Set<Integer> numbersFromUser = Set.of(1,2,3,4,5);

        //when
        NumberReceiverResponseDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("Failed");
    }

    @Test
    public void shouldReturnFailedWhenUserGaveMoreThanSixNumbers() {

        //given
        Set<Integer> numbersFromUser = Set.of(1,2,3,4,5,6,7);

        //when
        NumberReceiverResponseDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("Failed");
    }

    @Test
    public void shouldReturnSaveToDatabaseWhenUserGaveSixNumbers(){

        //given
        DrawDateGeneratorFacade drawDateGeneratorFacade = new DrawDateGeneratorFacade(clock);
        Set<Integer> numbersFromUser = Set.of(1,2,3,4,5,6);
        NumberReceiverResponseDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        LocalDateTime drawDate = drawDateGeneratorFacade.getNextDrawDate();

        //when
        List<TicketDto> ticketDtos = numberReceiverFacade.userNumbers(drawDate);

        //then
        assertThat(ticketDtos).contains(
                TicketDto.builder()
                        .hash(result.ticketDto().hash())
                        .drawDate(drawDate)
                        .numbers(result.ticketDto().numbers())
                        .build());
    }

    @Test
    void shouldReturnCorrectHash(){

        //given
        Set<Integer> numbersFromUser = Set.of(1,2,3,4,5,6);

        //when
        String response = numberReceiverFacade.inputNumbers(numbersFromUser)
                .ticketDto()
                .hash()
                .toString();

        //then
        assertThat(response).isNotNull();
        assertThat(response).hasSize(36);

    }

    @Test
    void ShouldReturnEmptyCollectionsIfGivenDataIsAfterNextDrawDate(){

        //given
        Clock clock =  Clock.fixed(LocalDateTime.of(2023, 9, 13, 10, 0, 0)
                .toInstant(ZoneOffset.UTC), ZoneId.of("Europe/London"));
        NumberReceiverFacade numberReceiverFacadeDraw = new NumberReceiverConfiguration().numberReceiverFacade(
                new TicketRepositoryTestImpl(),
                clock,
                new HashGeneratorImpl(),
                validatorFacade,
                new DrawDateGeneratorFacade(clock)
        );
        NumberReceiverResponseDto numberReceiverResponseDto =
                numberReceiverFacadeDraw.inputNumbers(Set.of(1,2,3,4,5,6));
        LocalDateTime drawDate = numberReceiverResponseDto.ticketDto().drawDate();

        //when
       List<TicketDto> allTicketsByDate = numberReceiverFacadeDraw.userNumbers(drawDate.plusWeeks(1));

       //then
        assertThat(allTicketsByDate).isEmpty();

    }



}
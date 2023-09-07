package pl.tomwodz.lottogame.domain.numberreceiver;

import org.junit.jupiter.api.Test;
import pl.tomwodz.lottogame.domain.AdjustableClock;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.NumberReceiverResponseDto;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest {
    AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2023,9,7, 14, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());

    NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(

            new NumberValidator(),
            new TicketRepositoryTestImpl(),
            clock,
            new HashGeneratorImpl(),
            new DrawDateGenerator(clock)
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
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        Set<Integer> numbersFromUser = Set.of(1,2,3,4,5,6);
        NumberReceiverResponseDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        LocalDateTime drawDate = drawDateGenerator.getNextDrawDate();

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



}
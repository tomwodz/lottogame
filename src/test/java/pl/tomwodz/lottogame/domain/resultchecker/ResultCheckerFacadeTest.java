package pl.tomwodz.lottogame.domain.resultchecker;

import org.junit.jupiter.api.Test;
import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.numbergenerator.NumberGeneratorFacade;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.WinningNumbersDto;
import pl.tomwodz.lottogame.domain.numberreceiver.NumberReceiverFacade;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.TicketDto;
import pl.tomwodz.lottogame.domain.resultchecker.dto.PlayersDto;
import pl.tomwodz.lottogame.domain.resultchecker.dto.ResultDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResultCheckerFacadeTest {

    PlayerRepository playerRepository = new PlayerRepositoryTestImpl();

    NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);

    DrawDateGeneratorFacade drawDateGeneratorFacade = mock(DrawDateGeneratorFacade.class);
    ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration()
            .resultCheckerFacade(numberReceiverFacade, numberGeneratorFacade, playerRepository, drawDateGeneratorFacade);

    LocalDateTime drawDate = LocalDateTime.of(2023, 9, 14, 12, 0, 0);

    @Test
    void invalidNullWinningNumbersShouldGenerateFailMessage() {

        //given
        when(drawDateGeneratorFacade.getNextDrawDate()).thenReturn(drawDate);
        when(numberGeneratorFacade.retrieveWinningNumberByDate(drawDate)).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(null)
                .build());

        //when
        PlayersDto playersDto = resultCheckerFacade.generateResults();

        //then
        String message = playersDto.message();
        assertThat(message).isEqualTo("Winners failed to retrieve");

    }

    @Test
    void WinningNumbersShouldGenerateSuccessMessage() {

        //given
        when(drawDateGeneratorFacade.getNextDrawDate()).thenReturn(drawDate);
        when(numberGeneratorFacade.retrieveWinningNumberByDate(drawDate)).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .build());

        //when
        PlayersDto playersDto = resultCheckerFacade.generateResults();

        //then
        String message = playersDto.message();
        assertThat(message).isEqualTo("Winners succeeded to retrieve");

    }


    @Test
    void invalidEmptyWinningNumbersShouldGenerateFailMessage() {

        //given
        when(drawDateGeneratorFacade.getNextDrawDate()).thenReturn(drawDate);
        when(numberGeneratorFacade.retrieveWinningNumberByDate(drawDate)).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(null)
                .build());

        //when
        PlayersDto playersDto = resultCheckerFacade.generateResults();

        //then
        String message = playersDto.message();
        assertThat(message).isEqualTo("Winners failed to retrieve");

    }


    @Test
    void ItShouldGenerateResultWithCorrectCredentials() {
        //given
        when(drawDateGeneratorFacade.getNextDrawDate()).thenReturn(drawDate);
        when(numberGeneratorFacade.retrieveWinningNumberByDate(drawDate)).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .build());
        String hash = "001";
        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate()).thenReturn(
                List.of(TicketDto.builder()
                                .hash(hash)
                                .numbers(Set.of(7, 8, 9, 10, 11, 12))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .hash("002")
                                .numbers(Set.of(7, 88, 19, 15, 11, 13))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .hash("003")
                                .numbers(Set.of(9, 18, 29, 30, 44, 14))
                                .drawDate(drawDate)
                                .build())
        );

        //when
        resultCheckerFacade.generateResults();
        ResultDto resultDto = resultCheckerFacade.findByTicketId(hash);


        //then
        ResultDto expectedResult = ResultDto.builder()
                .hash(hash)
                .numbers(Set.of(7, 8, 9, 10, 11, 12))
                .hitNumbers(Set.of())
                .drawDate(drawDate)
                .isWinner(false)
                .build();
        assertThat(resultDto).isEqualTo(expectedResult);
    }

    @Test
    void ItShouldGenerateAllPlayersWithCorrectMessage() {
        //given
        when(drawDateGeneratorFacade.getNextDrawDate()).thenReturn(drawDate);
        when(numberGeneratorFacade.retrieveWinningNumberByDate(drawDate)).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .build());
        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate()).thenReturn(
                List.of(TicketDto.builder()
                                .hash("001")
                                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .hash("002")
                                .numbers(Set.of(1, 2, 7, 8, 9, 10))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .hash("003")
                                .numbers(Set.of(7, 8, 9, 10, 11, 12))
                                .drawDate(drawDate)
                                .build())
        );
        //when

        PlayersDto playersDto = resultCheckerFacade.generateResults();

        //then
        List<ResultDto> results = playersDto.results();
        ResultDto resultDto = ResultDto.builder()
                .hash("001")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        ResultDto resultDto1 = ResultDto.builder()
                .hash("001")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        ResultDto resultDto2 = ResultDto.builder()
                .hash("001")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        assertThat(results).contains(resultDto, resultDto1, resultDto2);
        String message = playersDto.message();
        assertThat(message).isEqualTo("Winners succeeded to retrieve");

    }

}

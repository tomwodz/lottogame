package pl.tomwodz.lottogame.domain.resultannouncer;

import org.junit.jupiter.api.Test;
import pl.tomwodz.lottogame.domain.resultannouncer.dto.ResponseDto;
import pl.tomwodz.lottogame.domain.resultannouncer.dto.ResultResponseDto;
import pl.tomwodz.lottogame.domain.resultchecker.ResultCheckerFacade;
import pl.tomwodz.lottogame.domain.resultchecker.dto.ResultDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.tomwodz.lottogame.domain.resultannouncer.MessageResponse.*;

class ResultAnnouncerFacadeTest {

    ResultRepository resultRepository = new ResultRepositoryTestImpl();

    ResultCheckerFacade resultCheckerFacade = mock(ResultCheckerFacade.class);

    Clock clock = Clock.systemUTC();
    ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().resultAnnouncerFacade(resultRepository, resultCheckerFacade, clock);

    @Test
    void ItShouldReturnResponseWithLoseMessageIfTicketIsNotWinningTicket() {

        //given
        LocalDateTime drawDate = LocalDateTime.of(2023, 9, 14, 12, 0, 0);
        String hash = "111";
        ResultDto resultDto = ResultDto.builder()
                .hash("111")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of())
                .drawDate(drawDate)
                .isWinner(false)
                .build();

        when(resultCheckerFacade.findByTicketId(hash)).thenReturn(resultDto);

        //when
        ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(hash);

        //then
        ResponseDto responseDto = ResponseDto.builder()
                .hash("111")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of())
                .drawDate(drawDate)
                .isWinner(false)
                .build();

        ResultResponseDto expectedResult = new ResultResponseDto(responseDto, LOSE_MESSAGE.info);
        assertThat(resultResponseDto).isEqualTo(expectedResult);
    }

    @Test
    void ItShouldReturnResponseWithLoseMessageIfTicketIsWinningTicket() {

        //given
        LocalDateTime drawDate = LocalDateTime.of(2023, 9, 14, 12, 0, 0);
        String hash = "111";
        ResultDto resultDto = ResultDto.builder()
                .hash("111")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(6, 5, 4, 11, 12, 13))
                .drawDate(drawDate)
                .isWinner(true)
                .build();

        when(resultCheckerFacade.findByTicketId(hash)).thenReturn(resultDto);

        //when
        ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(hash);

        //then
        ResponseDto responseDto = ResponseDto.builder()
                .hash("111")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(6, 5, 4, 11, 12, 13))
                .drawDate(drawDate)
                .isWinner(true)
                .build();

        ResultResponseDto expectedResult = new ResultResponseDto(responseDto, WIN_MESSAGE.info);
        assertThat(resultResponseDto).isEqualTo(expectedResult);
    }

    @Test
   void ItShouldReturnResponseWithWaitMessageIfDateIsBeforeAnnouncementTime() {

        //given
        LocalDateTime drawDate = LocalDateTime.of(2023, 9, 14, 12, 0, 0);
        String hash = "111";
        Clock clock = Clock.fixed(LocalDateTime.of(2023, 9, 13, 12, 0, 0)
                .toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration()
                .resultAnnouncerFacade(resultRepository, resultCheckerFacade, clock);
        ResultDto resultDto = ResultDto.builder()
                .hash("111")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(6, 5, 4, 11, 12, 13))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.findByTicketId(hash)).thenReturn(resultDto);

        //when
        ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(hash);

        //then
        ResponseDto responseDto = ResponseDto.builder()
                .hash("111")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(6, 5, 4, 11, 12, 13))
                .drawDate(drawDate)
                .isWinner(true)
                .build();

        ResultResponseDto expectedResult = new ResultResponseDto(responseDto, WAIT_MESSAGE.info);
        assertThat(resultResponseDto).isEqualTo(expectedResult);
    }

    @Test
    void ItShouldReturnResponseWithHashDoesNotExistMessageIfHashDoesNotExist() {
        //given
        String hash = "111";
        when(resultCheckerFacade.findByTicketId(hash)).thenReturn(null);

        //when
        ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(hash);

        //then
        ResultResponseDto expectedResult = new ResultResponseDto(null, HASH_DOES_NOT_EXIST_MESSAGE.info);
        assertThat(resultResponseDto).isEqualTo(expectedResult);
    }

    @Test
    void ItShouldReturnResponseWithHashDoesNotExistMessageIfResponseIsNotSavedToDbYet() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023, 9, 14, 12, 0, 0);
        String hash = "111";
        ResultDto resultDto = ResultDto.builder()
                .hash("111")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(6, 5, 4, 11, 12, 13))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.findByTicketId(hash)).thenReturn(resultDto);
        ResultResponseDto resultResponseDtoTest = resultAnnouncerFacade.checkResult(hash);
        String test = resultResponseDtoTest.responseDto().hash();

        //when
        ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(test);

        //then
        ResultResponseDto expectedResult = new ResultResponseDto(
                resultResponseDto.responseDto(), ALREADY_CHECKED.info);
        assertThat(resultResponseDto).isEqualTo(expectedResult);
    }

}
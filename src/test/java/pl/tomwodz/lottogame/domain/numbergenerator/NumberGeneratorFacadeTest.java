package pl.tomwodz.lottogame.domain.numbergenerator;

import org.junit.jupiter.api.Test;
import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.WinningNumbersDto;
import pl.tomwodz.lottogame.domain.validator.ValidatorFacade;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NumberGeneratorFacadeTest {

    WinningNumbersRepository winningNumbersRepository = new WinningNumbersRepositoryTestImpl();
    NumberRandomGeneratorRepository numberRandomGeneratorRepository = new NumberRandomGeneratorRepositoryTestImpl();

    DrawDateGeneratorFacade drawDateGeneratorFacade = mock(DrawDateGeneratorFacade.class);
    ValidatorFacade validatorFacade = mock(ValidatorFacade.class);
    NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration()
            .numberGeneratorFacade(drawDateGeneratorFacade, numberRandomGeneratorRepository,
                    validatorFacade, winningNumbersRepository);
    @Test
    void ItShouldBeReturnWinningNumbersOfRequiredSize() {

        //given
        when(drawDateGeneratorFacade.getNextDrawDate()).thenReturn(LocalDateTime.now());
        //then
        WinningNumbersDto generatedNumbers = numberGeneratorFacade.generateWinningNumbers();

        //when
        assertThat(generatedNumbers.getWinningNumbers().size()).isEqualTo(6);
    }

    @Test
    void ItShouldReturnWinningNumbersOfRequiredSizeAndRequiredRange() {

        //given
        when(drawDateGeneratorFacade.getNextDrawDate()).thenReturn(LocalDateTime.now());

        //when
        WinningNumbersDto generatedNumbers = numberGeneratorFacade.generateWinningNumbers();

        //then
        int upperBand = 99;
        int lowerBand = 1;
        Set<Integer> winningNumbers = generatedNumbers.getWinningNumbers();
        boolean numbersInRange = winningNumbers.stream().allMatch(number -> number >= lowerBand && number <= upperBand);
        assertThat(numbersInRange).isTrue();

    }

    @Test
    void invalidNumberShouldThrowException() {

        //given
        Set<Integer> invalidNumbersOutOfRange = Set.of(100, 2, 3, 4, 5, 6);
        when(drawDateGeneratorFacade.getNextDrawDate()).thenReturn(LocalDateTime.now());
        when(validatorFacade.validationWinningNumbers(invalidNumbersOutOfRange)).thenThrow(IllegalStateException.class);
        NumberRandomGeneratorRepository repository
                = new NumberRandomGeneratorRepositoryTestImpl(invalidNumbersOutOfRange);
        NumberGeneratorFacade numberGeneratorFacadeInvalidNumber = new NumberGeneratorConfiguration()
                .numberGeneratorFacade(drawDateGeneratorFacade, repository,
                        validatorFacade, winningNumbersRepository);

        //when
        //then
        assertThrows(IllegalStateException.class, () ->
                numberGeneratorFacadeInvalidNumber.generateWinningNumbers(), "Number out of range!");
    }

    @Test
    void ItShouldReturnWinningNumbersOfUniqueValues() {

        //given
        when(drawDateGeneratorFacade.getNextDrawDate()).thenReturn(LocalDateTime.now());

        //when
        WinningNumbersDto winningNumbersDto = numberGeneratorFacade.generateWinningNumbers();
        int generatedNumbersSize = new HashSet<>(winningNumbersDto.getWinningNumbers()).size();

        //then
        assertThat(generatedNumbersSize).isEqualTo(6);
    }

    @Test
    void invalidDateShouldDrawException() {

        //given
        LocalDateTime drawDate = LocalDateTime.of(2023, 9, 20, 12, 0, 0);
        when(drawDateGeneratorFacade.getNextDrawDate()).thenReturn(drawDate);

        //when
        //then
        assertThrows(WinningNumbersNotFoundException.class, () -> numberGeneratorFacade.retrieveWinningNumberByDate(drawDate), "Not Found");
    }

    @Test
    void ItShouldReturnTrueIfNumberAreGeneratedByGivenDate() {

        //given
        LocalDateTime drawDate = LocalDateTime.of(2023, 9, 14, 12, 0, 0);
        when(drawDateGeneratorFacade.getNextDrawDate()).thenReturn(drawDate);
        Set<Integer> generatedWinningNumbers = Set.of(1, 2, 3, 4, 5, 6);
        String id = UUID.randomUUID().toString();
        WinningNumbers winningNumbers = new WinningNumbers(id, generatedWinningNumbers, drawDate);
        winningNumbersRepository.save(winningNumbers);

        //when
        boolean areWinningNumbersGeneratedByDate = numberGeneratorFacade.areWinningNumbersGeneratedByDate();

        //then
        assertTrue(areWinningNumbersGeneratedByDate);
    }

    @Test
    void IyShouldReturnWinningNumberByGivenDate() {

        //given
        LocalDateTime drawDate = LocalDateTime.of(2023, 9, 14, 12, 0, 0);
        Set<Integer> generatedWinningNumbers = Set.of(1, 2, 3, 4, 5, 6);
        String id = UUID.randomUUID().toString();
        WinningNumbers winningNumbers = new WinningNumbers(id, generatedWinningNumbers, drawDate);
        winningNumbersRepository.save(winningNumbers);
        when(drawDateGeneratorFacade.getNextDrawDate()).thenReturn(drawDate);

        //when
        WinningNumbersDto winningNumbersDto = numberGeneratorFacade.retrieveWinningNumberByDate(drawDate);

        //then
        WinningNumbersDto expectedWinningNumbersDto = WinningNumbersDto.builder()
                .date(drawDate)
                .winningNumbers(generatedWinningNumbers)
                .build();
        assertThat(expectedWinningNumbersDto).isEqualTo(winningNumbersDto);
    }
}
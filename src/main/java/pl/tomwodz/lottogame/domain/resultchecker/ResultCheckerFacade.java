package pl.tomwodz.lottogame.domain.resultchecker;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

import static pl.tomwodz.lottogame.domain.resultchecker.ResultCheckerMapper.mapPlayersToResults;

@AllArgsConstructor
@Log4j2
public class ResultCheckerFacade {

    private final NumberReceiverFacade numberReceiverFacade;
    private final NumberGeneratorFacade numberGeneratorFacade;
    private final PlayerRepository playerRepository;
    private final WinnerGenerator winnerGenerator;
    private final ResultFactory resultFactory;
    private final DrawDateGeneratorFacade drawDateGeneratorFacade;

    public PlayersDto generateResults() {
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate();
        List<Ticket> tickets = resultFactory.mapFromTicketsDtoToTickets(allTicketsByDate);
        LocalDateTime drawDate = drawDateGeneratorFacade.getNextDrawDate();
        WinningNumbersDto winningNumbersDto = numberGeneratorFacade.retrieveWinningNumberByDate(drawDate);
        Set<Integer> winningNumbers = winningNumbersDto.getWinningNumbers();
        if (winningNumbers == null || winningNumbers.isEmpty()) {
            return PlayersDto.builder()
                    .message("Winners failed to retrieve")
                    .build();
        }
        List<Player> players = winnerGenerator.retrieveWinners(tickets, winningNumbers);
        playerRepository.saveAll(players);
        log.info("Save result");
        return PlayersDto.builder()
                .results(mapPlayersToResults(players))
                .message("Winners succeeded to retrieve")
                .build();
    }

    public ResultDto findByTicketId(String hash) {
        Player player = playerRepository.findById(hash)
                .orElseThrow(() -> new PlayerResultNotFoundException("Not found for id: " + hash));
        return resultFactory.mapFromPlayerToResultDto(hash, player);
    }



}

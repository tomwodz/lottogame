package pl.tomwodz.lottogame.domain.resultchecker;

import lombok.AllArgsConstructor;
import pl.tomwodz.lottogame.domain.numbergenerator.NumberGeneratorFacade;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.WinningNumbersDto;
import pl.tomwodz.lottogame.domain.numberreceiver.NumberReceiverFacade;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.TicketDto;
import pl.tomwodz.lottogame.domain.resultchecker.dto.PlayersDto;
import pl.tomwodz.lottogame.domain.resultchecker.dto.ResultDto;

import java.util.List;
import java.util.Set;

import static pl.tomwodz.lottogame.domain.resultchecker.ResultCheckerMapper.mapPlayersToResults;

@AllArgsConstructor
public class ResultCheckerFacade {

    private final NumberReceiverFacade numberReceiverFacade;
    private final NumberGeneratorFacade numberGeneratorFacade;
    private final PlayerRepository playerRepository;
    private final WinnerGenerator winnerGenerator;
    private final ResultFactory resultFactory;

    public PlayersDto generateWinners() {
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate();
        List<Ticket> tickets = resultFactory.mapFromTicketsDtoToTickets(allTicketsByDate);
        WinningNumbersDto winningNumbersDto = numberGeneratorFacade.generateWinningNumbers();
        Set<Integer> winningNumbers = winningNumbersDto.getWinningNumbers();
        if (winningNumbers == null || winningNumbers.isEmpty()) {
            return PlayersDto.builder()
                    .message("Winners failed to retrieve")
                    .build();
        }
        List<Player> players = winnerGenerator.retrieveWinners(tickets, winningNumbers);
        playerRepository.saveAll(players);
        return PlayersDto.builder()
                .results(mapPlayersToResults(players))
                .message("Winners succeeded to retrieve")
                .build();
    }

    public ResultDto findByHash(String hash) {
        Player player = playerRepository.findById(hash).orElseThrow(() -> new RuntimeException("Not found"));
        return resultFactory.mapFromPlayerToResultDto(hash, player);
    }



}

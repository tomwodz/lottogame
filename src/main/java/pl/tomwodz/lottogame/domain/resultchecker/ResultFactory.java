package pl.tomwodz.lottogame.domain.resultchecker;

import pl.tomwodz.lottogame.domain.numberreceiver.dto.TicketDto;
import pl.tomwodz.lottogame.domain.resultchecker.dto.ResultDto;

import java.util.List;

class ResultFactory {

    Ticket mapFromTicketDtoToTicket (TicketDto ticketDto){
        return Ticket.builder()
                .hash(ticketDto.hash())
                .drawDate(ticketDto.drawDate())
                .numbers(ticketDto.numbers())
                .build();
    }

    List<Ticket> mapFromTicketsDtoToTickets(List<TicketDto> ticketsDto){
        return ticketsDto.stream()
                .map(ticketDto -> mapFromTicketDtoToTicket(ticketDto))
                .toList();
    }

    ResultDto mapFromPlayerToResultDto(String hash, Player player){
        return ResultDto.builder()
                .hash(hash)
                .numbers(player.numbers())
                .hitNumbers(player.hitNumbers())
                .drawDate(player.drawDate())
                .isWinner(player.isWinner())
                .build();
    }



}
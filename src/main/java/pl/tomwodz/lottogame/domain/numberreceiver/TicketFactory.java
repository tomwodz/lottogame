package pl.tomwodz.lottogame.domain.numberreceiver;

import pl.tomwodz.lottogame.domain.numberreceiver.dto.TicketDto;

class TicketFactory {

    Ticket mapFromTicketDtoToTicket (TicketDto ticketDto){
        return new Ticket(
                ticketDto.hash(),
                ticketDto.drawDate(),
                ticketDto.numbers());
    }
}

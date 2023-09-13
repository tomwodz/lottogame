package pl.tomwodz.lottogame.domain.numberreceiver;

import pl.tomwodz.lottogame.domain.numberreceiver.dto.TicketDto;

class TicketMapper {

    public static TicketDto mapFromTicket(Ticket ticket) {
        return TicketDto.builder().
                numbers(ticket.numbers())
                .hash(ticket.hash())
                .drawDate(ticket.drawDate())
                .build();
    }
}

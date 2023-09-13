package pl.tomwodz.lottogame.domain.numberreceiver.dto;

public record NumberReceiverResponseDto(
        TicketDto ticketDto,
        String message
) {
}

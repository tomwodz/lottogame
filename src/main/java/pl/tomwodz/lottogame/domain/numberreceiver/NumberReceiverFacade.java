package pl.tomwodz.lottogame.domain.numberreceiver;

import lombok.AllArgsConstructor;
import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.NumberReceiverResponseDto;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.TicketDto;
import pl.tomwodz.lottogame.domain.validator.ValidatorFacade;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class NumberReceiverFacade {

    private final TicketRepository ticketRepository;
    private final HashGenerator hashGenerator;
    private final ValidatorFacade validatorFacade;
    private final DrawDateGeneratorFacade drawDateGeneratorFacade;
    private final TicketFactory ticketFactory;

    public NumberReceiverResponseDto inputNumbers(Set<Integer> numbersFromUser) {
        if (validatorFacade.validationNumbers(numbersFromUser)) {
            String hash = hashGenerator.getHash();

            TicketDto generatedTicket = TicketDto.builder()
                    .hash(hash)
                    .drawDate(drawDateGeneratorFacade.getNextDrawDate())
                    .numbers(numbersFromUser)
                    .build();

            Ticket ticketToSave = ticketFactory.mapFromTicketDtoToTicket(generatedTicket);

            ticketRepository.save(ticketToSave);

            return new NumberReceiverResponseDto(generatedTicket, "Success");
        }
        return new NumberReceiverResponseDto(null, "Failed");
    }

    public List<TicketDto> userNumbers(LocalDateTime date){
        List<Ticket> allTicketsByDrawDate = ticketRepository.findAllTicketByDrawDate(date);
        return allTicketsByDrawDate.stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }

    public List<TicketDto> retrieveAllTicketsByNextDrawDate() {
        LocalDateTime nextDrawDate = drawDateGeneratorFacade.getNextDrawDate();
        return retrieveAllTicketsByNextDrawDate(nextDrawDate);
    }

    public List<TicketDto> retrieveAllTicketsByNextDrawDate(LocalDateTime date) {
        LocalDateTime nextDrawDate = drawDateGeneratorFacade.getNextDrawDate();
        if (date.isAfter(nextDrawDate)) {
            return Collections.emptyList();
        }
        return ticketRepository.findAllTicketByDrawDate(date)
                .stream()
                .filter(ticket -> ticket.drawDate().isEqual(date))
                .map(ticket -> TicketDto.builder()
                        .hash(ticket.hash())
                        .numbers(ticket.numbers())
                        .drawDate(ticket.drawDate())
                        .build())
                .toList();
    }

}

package pl.tomwodz.lottogame.domain.numberreceiver;

import lombok.AllArgsConstructor;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.NumberReceiverResponseDto;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.TicketDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class NumberReceiverFacade {

    private final NumberValidator validator;
    private final TicketRepository repository;
    private final Clock clock;
    private final HashGenerator hashGenerator;
    private final DrawDateGenerator drawDateGenerator;

    public NumberReceiverResponseDto inputNumbers(Set<Integer> numbersFromUser) {
        boolean areAllNumbersInRange = validator.areAllNumbersInRange(numbersFromUser);
        if (areAllNumbersInRange) {
            String hash = hashGenerator.getHash();
            LocalDateTime drawDate = drawDateGenerator.getNextDrawDate();

            TicketDto generatedTicket = TicketDto.builder()
                    .hash(hash)
                    .drawDate(drawDate)
                    .numbers(numbersFromUser)
                    .build();

            Ticket ticketToSave = Ticket.builder()
                    .hash(hash)
                    .drawDate(generatedTicket.drawDate())
                    .numbers(generatedTicket.numbers())
                    .build();


            repository.save(ticketToSave);

            return new NumberReceiverResponseDto(generatedTicket, "Success");
        }
        return new NumberReceiverResponseDto(null, "Failed");
    }

    public List<TicketDto> userNumbers(LocalDateTime date){
        List<Ticket> allTicketsByDrawDate = repository.findAllTicketByDrawDate(date);
        return allTicketsByDrawDate.stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }

}

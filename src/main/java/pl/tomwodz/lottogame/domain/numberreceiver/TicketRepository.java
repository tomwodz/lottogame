package pl.tomwodz.lottogame.domain.numberreceiver;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository {
    Ticket save(Ticket ticket);

    List<Ticket> findAllTicketByDrawDate(LocalDateTime date);

}

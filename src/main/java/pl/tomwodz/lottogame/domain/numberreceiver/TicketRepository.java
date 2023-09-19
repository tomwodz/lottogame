package pl.tomwodz.lottogame.domain.numberreceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, Long> {
    //Ticket save(Ticket ticket);

    List<Ticket> findAllTicketByDrawDate(LocalDateTime date);

}

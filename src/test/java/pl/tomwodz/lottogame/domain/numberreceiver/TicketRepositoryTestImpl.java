package pl.tomwodz.lottogame.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TicketRepositoryTestImpl implements TicketRepository {

    Map<String, Ticket> inMemoryDatabase = new ConcurrentHashMap<>();
    @Override
    public Ticket save(Ticket ticket) {
        inMemoryDatabase.put(ticket.hash(), ticket);
        return ticket;
    }

    @Override
    public List<Ticket> findAllTicketByDrawDate(LocalDateTime date) {
        return inMemoryDatabase.values()
                .stream()
                .filter(ticket -> ticket.drawDate().equals(date))
                .toList();
    }
}

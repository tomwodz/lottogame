package pl.tomwodz.lottogame.domain.resultchecker;


import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record Ticket(String hash,
                     LocalDateTime drawDate,
                     Set<Integer> numbers) {
}

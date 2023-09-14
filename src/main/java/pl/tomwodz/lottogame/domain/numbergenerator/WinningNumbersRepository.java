package pl.tomwodz.lottogame.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WinningNumbersRepository {

    WinningNumbers save(WinningNumbers winningNumbers);
    Optional<WinningNumbers> findByDate(LocalDateTime localDateTime);
    boolean existsByDate(LocalDateTime localDateTime);
}

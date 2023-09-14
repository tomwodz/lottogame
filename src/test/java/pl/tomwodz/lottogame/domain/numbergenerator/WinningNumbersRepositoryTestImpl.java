package pl.tomwodz.lottogame.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class WinningNumbersRepositoryTestImpl implements WinningNumbersRepository{

    private final Map<LocalDateTime, WinningNumbers> inMemoryDatabase = new ConcurrentHashMap<>();
    @Override
    public WinningNumbers save(WinningNumbers winningNumbers) {
        inMemoryDatabase.put(winningNumbers.getDate(), winningNumbers);
        WinningNumbers winningNumbersSaved = inMemoryDatabase.get(winningNumbers.getDate());
        return winningNumbersSaved;
    }

    @Override
    public Optional<WinningNumbers> findByDate(LocalDateTime date) {
        return Optional.ofNullable(inMemoryDatabase.get(date));
    }

    @Override
    public boolean existsByDate(LocalDateTime nextDrawDate) {
        if(inMemoryDatabase.containsKey(nextDrawDate)){
            return true;
        };
        return false;
    }

}

package pl.tomwodz.lottogame.domain.resultchecker;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerRepositoryTestImpl implements PlayerRepository{

    Map<String, Player> inMemoryDatabase = new ConcurrentHashMap<>();
    @Override
    public List<Player> saveAll(List<Player> players) {
        players.forEach(player -> inMemoryDatabase.put(player.hash(), player));
        return players;
    }

    @Override
    public Optional<Player> findById(String hash) {
        return Optional.ofNullable(inMemoryDatabase.get(hash));
    }
}

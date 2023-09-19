package pl.tomwodz.lottogame.domain.resultchecker;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface PlayerRepository {
    List<Player> saveAll(List<Player> players);

    Optional<Player> findById(String hash);
}

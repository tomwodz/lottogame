package pl.tomwodz.lottogame.domain.resultannouncer;

import java.util.Optional;

public interface ResultRepository {

    ResultResponse save(ResultResponse resultResponse);
    boolean existsById(String hash);

    Optional<ResultResponse> findById(String hash);

}

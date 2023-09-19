package pl.tomwodz.lottogame.domain.resultannouncer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ResultRepository extends MongoRepository<ResultResponse, String> {

    //ResultResponse save(ResultResponse resultResponse);
    boolean existsById(String hash);

    Optional<ResultResponse> findById(String hash);

}

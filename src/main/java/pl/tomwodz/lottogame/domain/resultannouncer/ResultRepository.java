package pl.tomwodz.lottogame.domain.resultannouncer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ResultRepository extends MongoRepository<ResultResponse, String> {

}

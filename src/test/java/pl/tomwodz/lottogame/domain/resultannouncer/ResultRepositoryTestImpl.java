package pl.tomwodz.lottogame.domain.resultannouncer;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ResultRepositoryTestImpl implements ResultRepository {

    private final Map<String, ResultResponse> inMemoryDatabase = new ConcurrentHashMap<>();
    @Override
    public ResultResponse save(ResultResponse resultResponse) {
        inMemoryDatabase.put(resultResponse.hash(), resultResponse);
        ResultResponse resultResponseSaved = inMemoryDatabase.get(resultResponse.hash());
        return resultResponseSaved ;
    }

    @Override
    public boolean existsById(String hash) {
        return inMemoryDatabase.containsKey(hash);
    }

    @Override
    public Optional<ResultResponse> findById(String hash) {
        return Optional.ofNullable(inMemoryDatabase.get(hash));
    }
}


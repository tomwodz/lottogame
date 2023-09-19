package pl.tomwodz.lottogame.domain.numberreceiver;
import java.util.UUID;

class HashGeneratorImpl implements HashGenerator {

    @Override
    public String getHash() {
        return UUID.randomUUID().toString();
    }
}

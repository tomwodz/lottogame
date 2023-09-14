package pl.tomwodz.lottogame.domain.resultannouncer;

public class ResultResponseNotFoundException extends RuntimeException{

    public ResultResponseNotFoundException(String message) {
        super(message);
    }

}

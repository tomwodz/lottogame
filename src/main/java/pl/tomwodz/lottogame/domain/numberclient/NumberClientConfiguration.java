package pl.tomwodz.lottogame.domain.numberclient;

public class NumberClientConfiguration {

    public NumberClientFacade numberClientFacade(){
        return new NumberClientFacade();
    }
}

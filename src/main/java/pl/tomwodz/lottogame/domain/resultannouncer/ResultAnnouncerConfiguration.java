package pl.tomwodz.lottogame.domain.resultannouncer;

import pl.tomwodz.lottogame.domain.resultchecker.ResultCheckerFacade;

import java.time.Clock;

public class ResultAnnouncerConfiguration {

    public ResultAnnouncerFacade resultAnnouncerFacade (ResultRepository resultRepository, ResultCheckerFacade checkerFacade, Clock clock){
        ResultResponseFactory resultResponseFactory = new ResultResponseFactory();
        return new ResultAnnouncerFacade(clock, resultRepository, checkerFacade, resultResponseFactory);
    }
}

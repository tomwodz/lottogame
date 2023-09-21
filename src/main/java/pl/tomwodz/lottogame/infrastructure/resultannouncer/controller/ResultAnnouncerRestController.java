package pl.tomwodz.lottogame.infrastructure.resultannouncer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tomwodz.lottogame.domain.resultannouncer.ResultAnnouncerFacade;
import pl.tomwodz.lottogame.domain.resultannouncer.dto.ResultResponseDto;
import pl.tomwodz.lottogame.domain.resultchecker.ResultCheckerFacade;

@RestController
@AllArgsConstructor
@RequestMapping("/results")
public class ResultAnnouncerRestController {

    private final ResultAnnouncerFacade resultAnnouncerFacade;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResultResponseDto> checkResultById(@PathVariable String id) {
        return ResponseEntity.ok(this.resultAnnouncerFacade.checkResult(id));
    }
}

package pl.tomwodz.lottogame.infrastructure.resultannouncer.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.tomwodz.lottogame.domain.numbergenerator.NumberGeneratorFacade;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.WinningNumbersDto;
import pl.tomwodz.lottogame.domain.resultannouncer.ResultAnnouncerFacade;
import pl.tomwodz.lottogame.domain.resultannouncer.dto.ResultResponseDto;
import pl.tomwodz.lottogame.domain.resultannouncer.dto.TicketIdRequestDto;
import pl.tomwodz.lottogame.domain.resultchecker.PlayerResultNotFoundException;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/results")
public class ResultAnnouncerViewController {

    private final ResultAnnouncerFacade resultAnnouncerFacade;
    private final NumberGeneratorFacade numberGeneratorFacade;

    @GetMapping
    public String getFormToCheckResult(Model model){
        model.addAttribute("ticketId", TicketIdRequestDto.builder().build());
        return "check-result";
    }

    @PostMapping
    public String postCheckResult(Model model, @ModelAttribute TicketIdRequestDto requestDto){
        try {
            ResultResponseDto resultResponseDto = this.resultAnnouncerFacade.checkResult(requestDto.id());
            WinningNumbersDto winningNumbersDto = this.numberGeneratorFacade
                    .retrieveWinningNumberByDate(resultResponseDto.responseDto().drawDate());
            model.addAttribute("result", resultResponseDto);
            model.addAttribute("winningNumbers", winningNumbersDto);
            return "result";
        } catch (PlayerResultNotFoundException exception){
            model.addAttribute("info", "Nie ma jeszcze wyników dla kuponu. Sprawdź wynniki po losowaniu.");
            return "info";
        }
    }
}

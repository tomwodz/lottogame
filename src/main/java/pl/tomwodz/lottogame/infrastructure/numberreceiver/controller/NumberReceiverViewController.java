package pl.tomwodz.lottogame.infrastructure.numberreceiver.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.numberreceiver.NumberReceiverFacade;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.InputViewNumbersRequestDto;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.NumberReceiverResponseDto;

import java.util.HashSet;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/inputNumbers")
public class NumberReceiverViewController {

    private final DrawDateGeneratorFacade drawDateGeneratorFacade;
    private final NumberReceiverFacade numberReceiverFacade;

    @GetMapping
    public String getInputNumbers(Model model){
        model.addAttribute("drawDate", this.drawDateGeneratorFacade.getNextDrawDate());
        model.addAttribute("inputNumbers", InputViewNumbersRequestDto.builder().build());
        return "input-numbers";
    }

    @PostMapping
    public String postInputNumbers(@ModelAttribute InputViewNumbersRequestDto requestDto,
                                   Model model){
        model.addAttribute("drawDate", this.drawDateGeneratorFacade.getNextDrawDate());
        Set<Integer> collect = createSetInputNumbers(requestDto);
        NumberReceiverResponseDto numberReceiverResponseDto = this.numberReceiverFacade.inputNumbers(collect);
        model.addAttribute("ticket", numberReceiverResponseDto);
        if(numberReceiverResponseDto.ticketDto() == null){
            model.addAttribute("info", "Podaj prawidłowe liczby.");
            return "info";
        }
        return "ticket";
    }

    private Set<Integer> createSetInputNumbers(InputViewNumbersRequestDto inputViewNumbersRequestDto){
        Set<Integer> inputNumbers = new HashSet<>();
        inputNumbers.add(inputViewNumbersRequestDto.first());
        inputNumbers.add(inputViewNumbersRequestDto.second());
        inputNumbers.add(inputViewNumbersRequestDto.third());
        inputNumbers.add(inputViewNumbersRequestDto.fourth());
        inputNumbers.add(inputViewNumbersRequestDto.fifth());
        inputNumbers.add(inputViewNumbersRequestDto.sixth());
        return inputNumbers;
    }

}

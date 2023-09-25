package pl.tomwodz.lottogame.infrastructure.numberreceiver.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;

@Controller
@AllArgsConstructor
public class CommonViewController {

    private final DrawDateGeneratorFacade drawDateGeneratorFacade;
    @GetMapping(path = "/")
    public String getHomepage(Model model){
        model.addAttribute("drawDate", this.drawDateGeneratorFacade.getNextDrawDate());
        return "index";
    }
}

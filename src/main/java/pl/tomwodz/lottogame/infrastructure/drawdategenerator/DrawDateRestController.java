package pl.tomwodz.lottogame.infrastructure.drawdategenerator;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.drawdategenerator.dto.DrawDateResponseDto;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/nextdrawdate")
@Log4j2
public class DrawDateRestController {

    private final DrawDateGeneratorFacade drawDateGeneratorFacade;

    @Operation(summary = "Next drawing date")
    @GetMapping
    public ResponseEntity<DrawDateResponseDto> getNextDrawDate() {
       return ResponseEntity.ok(this.drawDateGeneratorFacade.getNextDrawDateDto());
    }

}

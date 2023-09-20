package pl.tomwodz.lottogame.infrastructure.numberreceiver.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.tomwodz.lottogame.domain.numberreceiver.NumberReceiverFacade;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.NumberReceiverResponseDto;

import java.util.HashSet;
import java.util.Set;

@RestController
@Log4j2
@AllArgsConstructor
public class InputNumbersRestController {

    private final NumberReceiverFacade numberReceiverFacade;

    @PostMapping("/inputNumbers")
    public ResponseEntity<NumberReceiverResponseDto> inputNumbers(@RequestBody @Valid InputNumbersRequestDto requestDto) {
        log.info(requestDto.inputNumbers());
        Set<Integer> collect = new HashSet<>(requestDto.inputNumbers());
        NumberReceiverResponseDto numberReceiverResponseDto = this.numberReceiverFacade.inputNumbers(collect);
      return ResponseEntity.ok(numberReceiverResponseDto);
    }
}

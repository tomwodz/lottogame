package pl.tomwodz.lottogame.domain.numberclient;

import lombok.AllArgsConstructor;
import pl.tomwodz.lottogame.domain.numberclient.dto.OutsideRandomNumbersResponseDto;

import java.util.Set;

@AllArgsConstructor
public class NumberClientFacade {
    public OutsideRandomNumbersResponseDto getSixOutsideRandomNumbers(){
        return OutsideRandomNumbersResponseDto.builder().outsideSixRandomNumbers(Set.of(1,2,3,4,5,6)).build();
    }

}

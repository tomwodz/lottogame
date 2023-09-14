package pl.tomwodz.lottogame.domain.resultannouncer;

import pl.tomwodz.lottogame.domain.resultannouncer.dto.ResponseDto;

class ResultResponseFactory {

     ResultResponse mapFromResponseDtoToResultResponse(ResponseDto responseDto) {
        return ResultResponse.builder()
                .hash(responseDto.hash())
                .numbers(responseDto.numbers())
                .hitNumbers(responseDto.hitNumbers())
                .drawDate(responseDto.drawDate())
                .isWinner(responseDto.isWinner())
                .build();
    }

}

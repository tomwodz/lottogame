package pl.tomwodz.lottogame.domain.resultannouncer;

import pl.tomwodz.lottogame.domain.resultannouncer.dto.ResponseDto;
import pl.tomwodz.lottogame.domain.resultchecker.dto.ResultDto;

class ResultMapper {

    static ResponseDto mapFromResultToResponseDto(ResultResponse resultResponse){
        return ResponseDto.builder()
                .drawDate(resultResponse.drawDate())
                .hash(resultResponse.hash())
                .hitNumbers(resultResponse.hitNumbers())
                .numbers(resultResponse.numbers())
                .isWinner(resultResponse.isWinner())
                .build();
    }

    static ResponseDto mapFromResultDtoToResponseDto(ResultDto resultDto) {
        return ResponseDto.builder()
                .hash(resultDto.hash())
                .numbers(resultDto.numbers())
                .hitNumbers(resultDto.hitNumbers())
                .drawDate(resultDto.drawDate())
                .isWinner(resultDto.isWinner())
                .build();
    }


}

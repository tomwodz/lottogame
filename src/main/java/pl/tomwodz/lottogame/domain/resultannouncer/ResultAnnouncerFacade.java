package pl.tomwodz.lottogame.domain.resultannouncer;

import lombok.AllArgsConstructor;
import pl.tomwodz.lottogame.domain.resultannouncer.dto.ResponseDto;
import pl.tomwodz.lottogame.domain.resultannouncer.dto.ResultResponseDto;
import pl.tomwodz.lottogame.domain.resultchecker.ResultCheckerFacade;
import pl.tomwodz.lottogame.domain.resultchecker.dto.ResultDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import static pl.tomwodz.lottogame.domain.resultannouncer.MessageResponse.*;

@AllArgsConstructor
public class ResultAnnouncerFacade {


    private final Clock clock;
    private final ResultRepository resultRepository;
    private final ResultCheckerFacade checkerFacade;
    private final ResultResponseFactory resultResponseFactory;

    public ResultResponseDto checkResult(String hash) {
        if (resultRepository.existsById(hash)) {
            Optional<ResultResponse> resultResponseCached = resultRepository.findById(hash);
            if(resultResponseCached.isPresent()){
                return new ResultResponseDto(ResultMapper.
                        mapFromResultToResponseDto(resultResponseCached.get()), ALREADY_CHECKED.info);
            }
        }
        ResultDto resultDto = checkerFacade.findByTicketId(hash);
        if (resultDto == null) {
            return new ResultResponseDto(null, HASH_DOES_NOT_EXIST_MESSAGE.info);
        }
        ResponseDto responseDto = ResultMapper.mapFromResultDtoToResponseDto(resultDto);
        resultRepository.save(resultResponseFactory.mapFromResponseDtoToResultResponse(responseDto));
        if (resultRepository.existsById(hash) && !isAfterResultAnnouncementTime(resultDto)) {
            return new ResultResponseDto(responseDto, WAIT_MESSAGE.info);
        }
        if (checkerFacade.findByTicketId(hash).isWinner()) {
            return new ResultResponseDto(responseDto, WIN_MESSAGE.info);
        }
        return new ResultResponseDto(responseDto, LOSE_MESSAGE.info);
    }

    private boolean isAfterResultAnnouncementTime(ResultDto resultDto) {
        LocalDateTime announcementDateTime = resultDto.drawDate();
        return LocalDateTime.now(clock).isAfter(announcementDateTime);
    }


}

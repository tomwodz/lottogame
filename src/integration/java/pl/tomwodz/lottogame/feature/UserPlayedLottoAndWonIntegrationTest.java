package pl.tomwodz.lottogame.feature;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tomwodz.lottogame.BaseIntegrationTest;
import pl.tomwodz.lottogame.domain.numberclient.NumberClientQuery;
import pl.tomwodz.lottogame.domain.numberclient.dto.OutsideRandomNumbersResponseDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserPlayedLottoAndWonIntegrationTest extends BaseIntegrationTest {

    @Autowired
    NumberClientQuery numberClientQuery;

    @Test
    public void ShouldUserWinAndSystemShouldGenerateWinners() {
        //    step 1: external service returns 6 random numbers (1,2,4,5,6)

        //given
        //when
        OutsideRandomNumbersResponseDto outsideRandomNumbersResponseDto = numberClientQuery.getSixOutsideRandomNumbers();

        //then
        assertThat(outsideRandomNumbersResponseDto).isNotNull();
        assertThat(outsideRandomNumbersResponseDto.outsideSixRandomNumbers().size()).isEqualTo(6);
    }

    //    step 2: user made POST /inputNumbers with 6 numbers (1, 2, 3, 4, 5, 6) at 16-11-2022 10:00 and system returned OK(200) with message: “success” and Ticket (DrawDate:19.11.2022 12:00 (Saturday), TicketId: sampleTicketId)
//    step 3: system generated winning numbers for draw date: 19.11.2022 12:00
//    step 4: 3 days and 1 minute passed, and it is 1 minute after the draw date (19.11.2022 12:01)
//    step 5: system generated result for TicketId: sampleTicketId with draw date 19.11.2022 12:00, and saved it with 6 hits
//    step 6: 3 hours passed, and it is 1 minute after announcement time (19.11.2022 15:01)
//    step 7: user made GET /results/sampleTicketId and system returned 200 (OK)

}

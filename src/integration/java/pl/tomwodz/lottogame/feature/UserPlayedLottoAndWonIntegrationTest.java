package pl.tomwodz.lottogame.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.tomwodz.lottogame.BaseIntegrationTest;
import pl.tomwodz.lottogame.domain.numberclient.NumberClientQuery;
import pl.tomwodz.lottogame.domain.numbergenerator.NumberGeneratorFacade;
import pl.tomwodz.lottogame.domain.numbergenerator.WinningNumbersNotFoundException;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.NumberReceiverResponseDto;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserPlayedLottoAndWonIntegrationTest extends BaseIntegrationTest {

    @Autowired
    NumberClientQuery numberClientQuery;

    @Autowired
    NumberGeneratorFacade numberGeneratorFacade;

    @Autowired
    CriteriaForGenerateNumbersConfigurationProperties criteria;

    @Test
    public void ShouldUserWinAndSystemShouldGenerateWinners() throws Exception{
        //step 1: external service returns 6 random numbers (1,2,4,5,6)
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min="
                        +criteria.lowerBand()+"&max="
                        +criteria.upperBand()+"&count="
                        +criteria.countOut())
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25]
                                          """.trim()
                        )));

        //step 2: system generated winning numbers for draw date: 19.11.2022 12:00
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022,11,19,12,0,0);

        //when
        //then
        await()
                .atMost(Duration.ofSeconds(12))
                .pollInterval(Duration.ofSeconds(3))
                .until(() -> {
                            try {
                                return !numberGeneratorFacade.retrieveWinningNumberByDate(drawDate)
                                        .getWinningNumbers()
                                        .isEmpty();
                            } catch (WinningNumbersNotFoundException e) {
                                return false;
                            }
                        }
                );


        //step 3: user made POST /inputNumbers with 6 numbers (1, 2, 3, 4, 5, 6) at 16-11-2022 10:00 and system returned OK(200) with message: “success” and Ticket (DrawDate:19.11.2022 12:00 (Saturday), TicketId: sampleTicketId)
        //given
        //when
        ResultActions perform = mockMvc.perform(post("/inputNumbers")
                .content(
                        """
                        {
                        "inputNumbers": [1,2,3,4,5,6]
                        }
                        """
                ).contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult mvcResult = perform.andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        NumberReceiverResponseDto numberReceiverResponseDto = objectMapper.readValue(response, NumberReceiverResponseDto.class);
        assertAll(
                () -> assertThat(numberReceiverResponseDto.ticketDto().drawDate()).isEqualTo(drawDate),
                () ->  assertThat(numberReceiverResponseDto.ticketDto().hash()).isNotNull(),
                () ->  assertThat(numberReceiverResponseDto.message()).isEqualTo("Success")
        );





        //step 4: 3 days and 1 minute passed, and it is 1 minute after the draw date (19.11.2022 12:01)
        //clock.plusDaysAndMinutes(3,1);

    }






//step 5: system generated result for TicketId: sampleTicketId with draw date 19.11.2022 12:00, and saved it with 6 hits
//step 6: 3 hours passed, and it is 1 minute after announcement time (19.11.2022 15:01)
//step 7: user made GET /results/sampleTicketId and system returned 200 (OK)

}

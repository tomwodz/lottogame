package pl.tomwodz.lottogame.apivalidationerror;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.tomwodz.lottogame.BaseIntegrationTest;
import pl.tomwodz.lottogame.infrastructure.apivalidation.ApiValidationErrorResponseDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    public void ShouldReturn400BadRequestAndValidationMessageWhenRequestHasEmptyInputNumbers() throws Exception {

        //given
        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/inputNumbers")
                .content(
                        """
                        {
                        "inputNumbers": []
                        }
                        """
                ).contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorResponseDto resultDto = objectMapper.readValue(result, new TypeReference<>() {
        });
        assertThat(resultDto.errors()).containsExactlyInAnyOrder(
                "inputNumbers must not be empty"
        );
    }

    @Test
    public void ShouldReturn400BadRequestAndValidationMessageWhenRequestDoesNotHaveInputNumbers() throws Exception {

        //given
        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/inputNumbers")
                .content(
                        """
                                {
                                            
                                }
                                """
                ).contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorResponseDto resultDto = objectMapper.readValue(result, new TypeReference<>() {
        });
        assertThat(resultDto.errors()).containsExactlyInAnyOrder(
                "inputNumbers must not be null",
                "inputNumbers must not be empty"
        );
    }

}

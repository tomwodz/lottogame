package pl.tomwodz.lottogame.http.numberclient;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import pl.tomwodz.lottogame.domain.numberclient.NumberClientQuery;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;
import pl.tomwodz.lottogame.infrastructure.numberclient.http.NumberClientConfiguration;
import pl.tomwodz.lottogame.infrastructure.numberclient.http.NumberClientRestTemplateConfigurationProperties;
import wiremock.org.apache.hc.core5.http.HttpStatus;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class NumberClientQueryIntegrationTest extends NumberClientConfiguration {

    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    public static final String APPLICATION_JSON_CONTENT_TYPE_VALUE = "application/json";

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    public NumberClientQuery remoteNumberClientQuery() {
        NumberClientRestTemplateConfigurationProperties properties =
                NumberClientRestTemplateConfigurationProperties.builder()
                        .uri("http://localhost")
                        .port(wireMockServer.getPort())
                        .readTimeout(1500)
                        .build();
        RestTemplate restTemplate = restTemplate(restTemplateResponseErrorHandler(), properties);
        return remoteNumberClientQueryRestTemplate(restTemplate, properties);
    }

    CriteriaForGenerateNumbersConfigurationProperties propertiesTest =
            CriteriaForGenerateNumbersConfigurationProperties.
                    builder()
                    .count(6)
                    .lowerBand(1)
                    .upperBand(99)
                    .countOut(25)
                    .build();


    @Test
    public void ShouldThrowException500WhenFaultConnectionResetByPeer() {

        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));

        //when
        Throwable throwable = catchThrowable(() -> remoteNumberClientQuery()
                .getSixOutsideRandomNumbers(propertiesTest));

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");

    }

    @Test
    public void ShouldThrowException500WhenFaultEmptyResponse() {

        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.EMPTY_RESPONSE)));

        //when
        Throwable throwable = catchThrowable(() -> remoteNumberClientQuery()
                .getSixOutsideRandomNumbers(propertiesTest));

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    public void ShouldThrowException500WhenFaultMalformedResponseChunk() {
        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
        //when
        Throwable throwable = catchThrowable(() -> remoteNumberClientQuery()
                .getSixOutsideRandomNumbers(propertiesTest));

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    public void ShouldThrowException500WhenFaultRandomDataThenClose() {

        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.RANDOM_DATA_THEN_CLOSE)));

        //when
        Throwable throwable = catchThrowable(() -> remoteNumberClientQuery()
                .getSixOutsideRandomNumbers(propertiesTest));

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    public void ShouldThrowException204WhenStatusIs204NoContent() {

        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_NO_CONTENT)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withBody("""
                                [1, 2, 3, 4, 5, 6, 82, 82, 83, 83, 86, 57, 10, 81, 53, 93, 50, 54, 31, 88, 15, 43, 79, 32, 43]
                                          """.trim()
                        )));

        // when
        Throwable throwable = catchThrowable(() -> remoteNumberClientQuery()
                .getSixOutsideRandomNumbers(propertiesTest));

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("204 NO_CONTENT");
    }

    @Test
    void ShouldThrowException500WhenResponseDelayIs5000MsAndClientHas1000MsReadTimeout() {

        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withBody("""
                                [1, 2, 3, 4, 5, 6, 82, 82, 83, 83, 86, 57, 10, 81, 53, 93, 50, 54, 31, 88, 15, 43, 79, 32, 43]
                                          """.trim()
                        )
                        .withFixedDelay(5000)));

        //when
        Throwable throwable = catchThrowable(() -> remoteNumberClientQuery()
                .getSixOutsideRandomNumbers(propertiesTest));

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    void ShouldThrowException404WhenHttpServiceReturningNotFoundStatus() {

        //given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withStatus(HttpStatus.SC_NOT_FOUND))
        );

        //when
        Throwable throwable = catchThrowable(() -> remoteNumberClientQuery()
                .getSixOutsideRandomNumbers(propertiesTest));

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("404 NOT_FOUND");
    }

}

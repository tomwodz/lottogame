package pl.tomwodz.lottogame.infrastructure.numberclient;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.tomwodz.lottogame.domain.numberclient.NumberClientQuery;
import pl.tomwodz.lottogame.domain.numberclient.dto.OutsideRandomNumbersResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
public class NumberClientRestTemplate implements NumberClientQuery {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public OutsideRandomNumbersResponseDto getSixOutsideRandomNumbers() {
        String urlForService = getUrlForService("/api/v1.0/random");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                .queryParam("min", 1)
                .queryParam("max", 99)
                .queryParam("count", 6)
                .toUriString();
        ResponseEntity<List<Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Integer>>() {
                });
        log.info(response);
        return OutsideRandomNumbersResponseDto.builder()
                .outsideSixRandomNumbers(response.getBody().stream().collect(Collectors.toSet()))
                .build();
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }


}

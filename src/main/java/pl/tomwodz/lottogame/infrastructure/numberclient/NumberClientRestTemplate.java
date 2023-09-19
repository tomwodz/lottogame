package pl.tomwodz.lottogame.infrastructure.numberclient;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.tomwodz.lottogame.domain.numberclient.NumberClientQuery;
import pl.tomwodz.lottogame.domain.numberclient.dto.OutsideRandomNumbersResponseDto;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
public class NumberClientRestTemplate implements NumberClientQuery {
    public static final String RANDOM_NUMBER_SERVICE_PATH = "/api/v1.0/random";

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public OutsideRandomNumbersResponseDto getSixOutsideRandomNumbers(CriteriaForGenerateNumbersConfigurationProperties criteria) {
        log.info("Started connecting to outside client.");
        try{
            ResponseEntity<List<Integer>> response = makeGetRequest(criteria);
            log.info("Success connecting. Get body to verification.");
            Set<Integer> verifiedResponse = getSixRandomDistinctNumbers(response);
            return OutsideRandomNumbersResponseDto.builder()
                    .outsideSixRandomNumbers(verifiedResponse)
                    .build();
        }
    catch (ResourceAccessException e) {
        log.error("Error connecting to outside client " + e.getMessage());
        return OutsideRandomNumbersResponseDto.builder()
                .outsideSixRandomNumbers(Collections.emptySet())
                .build();
    }

    }

    private ResponseEntity<List<Integer>> makeGetRequest(CriteriaForGenerateNumbersConfigurationProperties criteria) {
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final String url = UriComponentsBuilder.fromHttpUrl(getUrlForService(RANDOM_NUMBER_SERVICE_PATH))
                .queryParam("min", criteria.lowerBand())
                .queryParam("max", criteria.upperBand())
                .queryParam("count", criteria.count())
                .toUriString();
        ResponseEntity<List<Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return response;
    }

    private Set<Integer> getSixRandomDistinctNumbers(ResponseEntity<List<Integer>> response) {
        List<Integer> numbers = response.getBody();
        if (numbers == null) {
            log.error("Response Body was null returning empty collection");
            return Collections.emptySet();
        }
        Set<Integer> verifiedSet = numbers.stream()
                .distinct()
                .limit(6)
                .collect(Collectors.toSet());
        if(verifiedSet.size() == 6){
            log.info("Success Response Body Returned: " + response);
            return verifiedSet;
        } else {
            log.error("Response Body don't have 6 distinct numbers.");
            return Collections.emptySet();
        }
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }


}

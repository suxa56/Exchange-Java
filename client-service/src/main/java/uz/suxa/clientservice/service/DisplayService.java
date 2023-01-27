package uz.suxa.clientservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import uz.suxa.clientservice.dto.CurrencyRateDto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DisplayService {

    private List<CurrencyRateDto> ratesDto = new ArrayList<>();
    private static final String apiUrl = "http://localhost:8091/api/currencies";

    @Async
    public CompletableFuture<List<CurrencyRateDto>> getFormattedRates() {
        retrieveApi();
        return CompletableFuture.completedFuture(ratesDto);
    }

    public void saveHistory(String history) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");

        headers.setAll(map);

        HttpEntity<?> request = new HttpEntity<>(history, headers);
        String url = "http://localhost:8091/history/save/";

        ResponseEntity<?> response = new RestTemplate().postForEntity(url, request, String.class);
    }

    private void retrieveApi() {
        ratesDto.clear();
        ratesDto = jsonToList();
    }

    private List<CurrencyRateDto> jsonToList() {
        ObjectMapper objectMapper = new ObjectMapper();
        URL url;
        List<CurrencyRateDto> rateDtos;
        try {
            url = new URL(apiUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid string url");
        }
        try {
            rateDtos = objectMapper.readValue(url, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("cannot parse json from url to object");
        }
        return rateDtos;
    }
}

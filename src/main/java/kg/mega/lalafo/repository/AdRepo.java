package kg.mega.lalafo.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.mega.lalafo.model.dto.AdApiDto;
import kg.mega.lalafo.model.dto.ApiResultDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class AdRepo {

    private static final String LALAFO_API_URL = "https://lalafo.kg/api/search/v3/feed?expand=url&per-page=100&vip_count=0&m-name=last_push_up&sub-empty=1&page=1";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AdRepo(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<AdApiDto> fetchProducts() {

        HttpHeaders headers = createHeaders();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                LALAFO_API_URL, HttpMethod.GET, entity, String.class);


        ApiResultDto result = null;
        try {
            result = objectMapper.readValue(response.getBody(), ApiResultDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return result.getItems();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        headers.set("Referer", "https://lalafo.kg/");
        headers.set("Accept", "application/json, text/plain, */*");
        headers.set("Accept-Language", "ru-RU,ru;q=0.9,en;q=0.8");
        headers.set("Cookie", "affnity=1760023429.703.1952.364562");
        headers.set("Country-Id", "12");
        headers.set("Device", "pc");
        headers.set("Language", "ru_RU");
        headers.set("App-Id", "web");
        return headers;
    }
}

package nl.nigelchristiaans.weatherbackend;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestSpringBootController {
    @Value("${weatherApiKey}")
    public String apiKey;
    private final String baseUrl = "http://api.weatherapi.com/v1/current.json";

    @GetMapping("/weather")
    public String getWeather() {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "?key=" + apiKey + "&q=Amsterdam";
        return restTemplate.getForObject(url, String.class);
    }
}

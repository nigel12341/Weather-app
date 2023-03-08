package nl.nigelchristiaans.weatherbackend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestSpringBootControllerV1 {
    @Value("${weatherApiKey}")
    public String apiKey;

    @CrossOrigin
    @GetMapping("/v1/weather/current")
    public Object getWeather(@RequestParam String location) {
        final String baseUrl = "http://api.weatherapi.com/v1/current.json?key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "&q=" + location + "&aqi=yes";
        return restTemplate.getForObject(url, Object.class);
    }

    @CrossOrigin
    @GetMapping("/v1/weather/forecast")
    public Object getForecast(@RequestParam String location, @RequestParam(defaultValue = "7") int days) {
        final String baseUrl = "http://api.weatherapi.com/v1/forecast.json?key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "&q=" + location + "&aqi=yes&days=" + days;
        return restTemplate.getForObject(url, Object.class);
    }

    @CrossOrigin
    @GetMapping("/v1/autocomplete")
    public String getAutoCompleteLocation(@RequestParam String location) {
        final String baseUrl = "http://api.weatherapi.com/v1/search.json?key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "&q=" + location;
        return restTemplate.getForObject(url, String.class);
    }

    @CrossOrigin
    @GetMapping("/v1/weather/future")
    public Object getFutureWeather(@RequestParam String location, @RequestParam String date) {
        final String baseUrl = "http://api.weatherapi.com/v1/future.json?key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "&q=" + location + "&dt=" + date;
        return restTemplate.getForObject(url, Object.class);
    }

    @CrossOrigin
    @GetMapping("/v1/astronomy")
    public Object getAstronomy(@RequestParam String location, @RequestParam String date) {
        final String baseUrl = "http://api.weatherapi.com/v1/astronomy.json?key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "&q=" + location + "&dt=" + date;
        return restTemplate.getForObject(url, Object.class);
    }

    @CrossOrigin
    @GetMapping("/v1/timezone")
    public Object getTimezone(@RequestParam String location) {
        final String baseUrl = "http://api.weatherapi.com/v1/timezone.json?key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "&q=" + location;
        return restTemplate.getForObject(url, Object.class);
    }
}

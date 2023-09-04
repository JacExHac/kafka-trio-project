import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.WeatherLogDto;
import dtos.WeatherResponseDto;
import dtos.WeatherStatusDto;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.Random;

@ApplicationScoped
@Path("/send")
public class LogTrackerAndGenerator {
    @Inject
    @Channel("weather-status")
    Emitter<WeatherStatusDto> emitter;

    @Inject
    ObjectMapper objectMapper;


    @Scheduled(every = "10s")
    public void generateWeatherStatus() {
        WeatherStatusDto weatherStatusDto = randomWeatherStatusDto();
        emitter.send(weatherStatusDto);
        System.out.println("POSTED ON KAFKA TOPIC: " + weatherStatusDto.toString());
    }

    private WeatherStatusDto randomWeatherStatusDto() {
        String[] cities = {"Rijeka", "Split", "Zagreb", "Osijek"};
        Random random = new Random();
        int cityIndex = random.nextInt(cities.length);
        String selectedCity = cities[cityIndex];

        int minTemperature = 15;
        int maxTemperature = 40;
        int randomTemperature = random.nextInt(maxTemperature - minTemperature + 1) + minTemperature;

        int randomChanceOfRain = random.nextInt(101);

        return new WeatherStatusDto(selectedCity, randomTemperature, randomChanceOfRain);
    }

    @Incoming("processed-requests")
    public void processedRequestsPrinting(String processedRequests) throws JsonProcessingException {


        WeatherLogDto weatherLogDto = objectMapper.readValue(processedRequests, WeatherLogDto.class);

        System.out.print("=========PROCESSED REQUEST=========:\n" +
                 weatherLogDto +
                "\n================================================");

    }
}

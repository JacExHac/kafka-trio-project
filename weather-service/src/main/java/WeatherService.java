import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.WeatherLogDto;
import dtos.WeatherResponseDto;
import dtos.WeatherStatusDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class WeatherService {

    private WeatherStatusDto lastWeatherStatus;
    private static boolean sentNewestStatus = false;
    @Inject
    @Channel("processed-requests")
    Emitter<WeatherLogDto> processedRequestEmitter;

    @Inject()
    @Channel("weather-response")
    Emitter<WeatherResponseDto> weatherResponseEmitter;

    @Inject
    ObjectMapper objectMapper;

    @Incoming("weather-status")
    public void processWeatherStatus(String weatherStatusDtoString) throws JsonProcessingException {
        WeatherStatusDto weatherStatusDto = objectMapper.readValue(weatherStatusDtoString, WeatherStatusDto.class);
        System.out.println("New weather status just came in: " + weatherStatusDto.toString());
        lastWeatherStatus = new WeatherStatusDto(weatherStatusDto);
        sentNewestStatus = false;
        processWeatherRequest("update-called");
    }


    @Incoming("weather-request")
    public void consumeWeatherRequest(String sessionId) {
        System.out.println("A NEW REQUEST CAME IN, THE CLIENTS' SESSIONID FROM WEBSOCKET IS: " + sessionId);
        processWeatherRequest(sessionId);

    }

    public void processWeatherRequest(String sessionId) {
        String localIpAddress = System.getenv("SERVICE_IP");
        if(!sentNewestStatus) {
            System.out.println("I HAVEN'T SENT THE LATEST WEATHER REPORT, SENDING NOW");

            WeatherResponseDto weatherResponseDto = new WeatherResponseDto(localIpAddress, lastWeatherStatus.getCity(), lastWeatherStatus.getTemperature(), lastWeatherStatus.getChanceOfRain());
            weatherResponseEmitter.send(weatherResponseDto);
            sentNewestStatus = true;
            WeatherLogDto weatherLogDto = new WeatherLogDto(localIpAddress, sessionId, lastWeatherStatus.getCity(), lastWeatherStatus.getTemperature(), lastWeatherStatus.getChanceOfRain());
            processedRequestEmitter.send(weatherLogDto);
        }
    }
}

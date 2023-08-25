import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class WeatherService {

    private WeatherStatusDto lastWeatherReport = new WeatherStatusDto();
    @Inject
    @Channel("processed-requests")
    Emitter<ProcRequestDto> emitter;

    @Inject
    ObjectMapper objectMapper;

 /*   @Incoming("weather-report")
    public void processWeatherReport(Message<WeatherReportDto> weatherReportMessage) {
        WeatherReportDto weatherReport = weatherReportMessage.getPayload();
        setLastReadWeatherReport(weatherReport);
    }*/

    @Incoming("weather-status")
    //@Consumes(MediaType.APPLICATION_JSON)
    public void processWeatherStatus(String weatherStatusDtoString) throws JsonProcessingException {
        WeatherStatusDto weatherStatusDto1 = objectMapper.readValue(weatherStatusDtoString, WeatherStatusDto.class);
    }

    private void setLastReadWeatherReport(WeatherStatusDto weatherReport) {
        lastWeatherReport = weatherReport;
    }


    @Incoming("weather-request")
    public void processWeatherRequest(String ipAddress) {

    }
}

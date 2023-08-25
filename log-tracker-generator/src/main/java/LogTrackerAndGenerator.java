import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.ProcRequestDto;
import dtos.WeatherStatusDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
@Path("/send")
public class LogTrackerAndGenerator {
    @Inject
    @Channel("weather-status")
    Emitter<WeatherStatusDto> emitter;

    @Inject
    ObjectMapper objectMapper;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void generateWeatherStatus(WeatherStatusDto weatherStatusDto) {
        emitter.send(weatherStatusDto);
        System.out.println("POSTED ON KAFKA TOPIC: " + weatherStatusDto.toString());
    }

    @Incoming("processed-requests")
    public void processedRequestsPrinting(String processedRequests) throws JsonProcessingException {
        System.out.println("STRING VERSION : " + processedRequests);


        ProcRequestDto procRequestDto = objectMapper.readValue(processedRequests, ProcRequestDto.class);
        System.out.println("PROC TESTER: " + procRequestDto);

        System.out.print("=========PROCESSED REQUEST=========:\n" +
                "IP ADDRESS OF SERVICE: " + procRequestDto.getServiceIp() +
                "\nIP ADDRESS OF CLIENT: " + procRequestDto.getClientIp() +
                "\nWEATHER STATUS REPORT: " + procRequestDto.getWeatherStatusDto().toString() +
                "\n================================================");

    }
}

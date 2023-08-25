import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
@Path("/send")
public class LogTrackerAndGenerator {
    @Inject
    @Channel("weather-status")
    Emitter<WeatherStatusDto> emitter;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void generateWeatherStatus(WeatherStatusDto weatherStatusDto) {
        emitter.send(weatherStatusDto);
        System.out.println("POSTED ON KAFKA TOPIC: " + weatherStatusDto.toString());
    }
}

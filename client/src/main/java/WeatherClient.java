import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
@Path("/hello")
public class WeatherClient {

    @Inject
    @Channel("weather-request")
    Emitter<String> emitter;


    @GET
    public String sendKafkaMessage() {
        String message = "Requesting weather data...";
        emitter.send(message);


        return "Message sent: " + message;
    }

}

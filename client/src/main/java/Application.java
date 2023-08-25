import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Path("/hello")
public class Application {

    @Inject
    @Channel("weather-request")
    Emitter<String> emitter;

    @GET
    public String sendMessage() throws UnknownHostException {

        String ipAddress = Inet4Address.getLocalHost().getHostAddress();

        OutgoingKafkaRecordMetadata<?> metadata = OutgoingKafkaRecordMetadata.builder()
                .withTopic("weather-request")
                .withKey(Inet4Address.getLocalHost().getHostAddress())
                .build();
        emitter.send(Message.of(ipAddress).addMetadata(metadata));

        return "Sent request";
    }

    @POST
    @Path("/weather")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getWeatherReport(WeatherReportDto weatherReportDto) {
        return Response.status(Response.Status.OK).build();
    }


}
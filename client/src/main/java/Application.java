import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Path("/client-weather")
public class Application {

    @Inject
    @Channel("weather-request")
    Emitter<String> emitter;

    @Inject
    @ConfigProperty(name ="quarkus.http.port")
    int httpPort;

    @GET
    public Response sendMessage() throws UnknownHostException {

        String ipAddress = System.getenv("APP_IP");
        //TODO: map processed request correctly!!!!
        String data = "http://" + ipAddress + ":" + String.valueOf(httpPort) + "/client-weather";

        System.out.println("Here is the \"URL\": " + data);

        System.out.println("INET4ADDRESS: " + Inet4Address.getLocalHost().getHostAddress());

        OutgoingKafkaRecordMetadata<?> metadata = OutgoingKafkaRecordMetadata.builder()
                .withTopic("weather-request")
                .withKey(Inet4Address.getLocalHost().getHostAddress())
                .build();
        emitter.send(Message.of(data).addMetadata(metadata));

        System.out.println("Sent message");
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getWeatherReport(WeatherStatusDto weatherStatusDto) {
        System.out.println("Received weather report: " + weatherStatusDto.toString());
        return Response.status(Response.Status.OK).entity(weatherStatusDto.toString()).build();
    }


}
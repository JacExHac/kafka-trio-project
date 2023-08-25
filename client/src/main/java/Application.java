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
    public void sendMessage() throws UnknownHostException {

        String ipAddress = Inet4Address.getLocalHost().getHostAddress();
        //TODO: map processed request correctly!!!!
        String data = "http://" + /*ipAddress */ "127.0.0.1" + ":" + String.valueOf(httpPort) + "/client-weather";


        OutgoingKafkaRecordMetadata<?> metadata = OutgoingKafkaRecordMetadata.builder()
                .withTopic("weather-request")
                .withKey(Inet4Address.getLocalHost().getHostAddress())
                .build();
        emitter.send(Message.of(data).addMetadata(metadata));

        System.out.println("Sent message");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getWeatherReport(WeatherStatusDto weatherStatusDto) {
        System.out.println("Received weather report: " + weatherStatusDto.toString());
        return Response.status(Response.Status.OK).build();
    }


}
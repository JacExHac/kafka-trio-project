import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
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

}
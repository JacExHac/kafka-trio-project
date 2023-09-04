import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.WeatherResponseDto;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.inject.Inject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import org.eclipse.microprofile.reactive.messaging.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/client")
public class Application {

    @Inject
    ObjectMapper objectMapper;

    @Inject
    @Channel("weather-request")
    Emitter<String> emitter;

    private static Map<String, Session> pendingRequests = new ConcurrentHashMap<>(4);

    @OnOpen
    public void onOpen(Session session) {

        CompletableFuture.runAsync(() -> {
            try {
                session.getBasicRemote().sendText("Type \"WEATHER\" if you would like the next weather report in our country.\nIf you would like to disconnect type DISCONNECT.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(">>>>CLIENT WITH ID " + session.getId() + " CONNECTED");
        });
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println(">>>>CLIENT WITH ID " + session.getId() + " DISCONNECTED");
    }

    @OnMessage
    public void onMessage(String request, Session session) throws IOException {
        if(request.equals("WEATHER")) {
            pendingRequests.put(session.getId(), session);
            sendMessageRequest(session.getId());
        } else if(request.equals("DISCONNECT")) {
            pendingRequests.remove(session.getId(), session);
            session.close();
        }
    }

    public void sendMessageRequest(String sessionId) throws UnknownHostException {
        OutgoingKafkaRecordMetadata<?> metadata = OutgoingKafkaRecordMetadata.builder()
                .withTopic("weather-request")
                .withKey(sessionId)
                .build();
        emitter.send(Message.of(sessionId).addMetadata(metadata));
        System.out.println(">>>>>Sent message request to topic for client whose sessionId is " + sessionId);
    }

    @Incoming("weather-response")
    public void processWeatherResponse(String weatherStatusString) throws IOException {
        System.out.println();

        WeatherResponseDto weatherResponseDto = objectMapper.readValue(weatherStatusString, WeatherResponseDto.class);

        for(Session session : pendingRequests.values()) {
            System.out.println(session.getRequestURI() + session.toString());
        }

        if(!pendingRequests.isEmpty()) {
            for(Session s : pendingRequests.values()) {
                s.getBasicRemote().sendText(weatherResponseDto.toString());
            }
            pendingRequests.clear();
        }
    }


}
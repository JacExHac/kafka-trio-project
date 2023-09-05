import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dtos.WeatherResponseDto;
import jakarta.websocket.Session;
public class WebSocketManager {
    public static final Map<String, Session> pendingRequests = new ConcurrentHashMap<>();

    public static void addSession(String sessionId, Session session) {
        pendingRequests.put(sessionId, session);
    }

    public static void removeSession(String sessionId) {
        pendingRequests.remove(sessionId);
    }

    public static void sendWeatherResponseToAllSessions(WeatherResponseDto weatherResponseDto) throws IOException {
        for (Session session : pendingRequests.values()) {
            session.getBasicRemote().sendText(weatherResponseDto.toString());
        }
        pendingRequests.clear();
    }
}

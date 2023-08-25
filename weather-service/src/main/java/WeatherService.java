import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.ProcRequestDto;
import dtos.WeatherStatusDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

@ApplicationScoped
public class WeatherService {

    private WeatherStatusDto lastWeatherStatus = new WeatherStatusDto();
    @Inject
    @Channel("processed-requests")
    Emitter<String> emitter;


    @Inject
    ObjectMapper objectMapper;

    @Incoming("weather-status")
    //@Consumes(MediaType.APPLICATION_JSON)
    public void processWeatherStatus(String weatherStatusDtoString) throws JsonProcessingException {
        WeatherStatusDto weatherStatusDto = objectMapper.readValue(weatherStatusDtoString, WeatherStatusDto.class);
        lastWeatherStatus.setCity(weatherStatusDto.getCity());
        lastWeatherStatus.setTemperature(weatherStatusDto.getTemperature());
        lastWeatherStatus.setChanceOfRain(weatherStatusDto.getChanceOfRain());
        System.out.println("New weather status just came in: " + lastWeatherStatus.toString());
    }


    @Incoming("weather-request")
    public void processWeatherRequest(String targetUrl) throws UnknownHostException, MalformedURLException, JsonProcessingException {

        //TODO: map processed request correctly!!!!
        Client client = ClientBuilder.newClient();
        Response response = client.target(targetUrl)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(lastWeatherStatus));
        System.out.println("Sent values: " + lastWeatherStatus.toString() + "TO URL " + targetUrl);

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            // Request successful

            URL url = new URL(targetUrl);
            String ipAddress = url.getHost();

            ProcRequestDto procRequestDto = new ProcRequestDto();
            String localIpAddress = System.getenv("SERVICE_IP");
            procRequestDto.setServiceIp(localIpAddress);
            procRequestDto.setClientIp(ipAddress);
            procRequestDto.setWeatherStatusDto(new WeatherStatusDto(lastWeatherStatus));
            System.out.println("PROCESSED REQUEST DTO: " + procRequestDto);
            String procRequestAsString = objectMapper.writeValueAsString(procRequestDto);

            System.out.println(procRequestAsString);

            emitter.send(procRequestAsString);
        } else {
            System.out.println("Request failed with status: " + response.getStatus());
        }
        client.close();



    }
}

package dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherResponseDto {

    private String serviceIp;

    private String city;
    private int temperature;
    private int chanceOfRain;

    public String getServiceIp() {
        return serviceIp;
    }

    public void setServiceIp(String serviceIp) {
        this.serviceIp = serviceIp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getChanceOfRain() {
        return chanceOfRain;
    }

    public void setChanceOfRain(int chanceOfRain) {
        this.chanceOfRain = chanceOfRain;
    }

    public WeatherResponseDto(String serviceIp, String city, int temperature, int chanceOfRain) {
        this.serviceIp = serviceIp;
        this.city = city;
        this.temperature = temperature;
        this.chanceOfRain = chanceOfRain;
    }

    @Override
    public String toString() {
        return "WeatherResponseDto{" +
                "serviceIp='" + serviceIp + '\'' +
                ", city='" + city + '\'' +
                ", temperature=" + temperature +
                ", chanceOfRain=" + chanceOfRain +
                '}';
    }
}

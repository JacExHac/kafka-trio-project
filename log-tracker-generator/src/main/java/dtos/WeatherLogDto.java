package dtos;

public class WeatherLogDto {
    private String serviceIp;

    private String sessionId;

    private String city;
    private int temperature;
    private int chanceOfRain;

    public String getServiceIp() {
        return serviceIp;
    }

    public void setServiceIp(String serviceIp) {
        this.serviceIp = serviceIp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public WeatherLogDto(String serviceIp, String sessionId, String city, int temperature, int chanceOfRain) {
        this.serviceIp = serviceIp;
        this.sessionId = sessionId;
        this.city = city;
        this.temperature = temperature;
        this.chanceOfRain = chanceOfRain;
    }

    public WeatherLogDto() {
    }

    @Override
    public String toString() {
        return "WeatherLogDto{" +
                "serviceIp='" + serviceIp + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", city='" + city + '\'' +
                ", temperature=" + temperature +
                ", chanceOfRain=" + chanceOfRain +
                '}';
    }
}

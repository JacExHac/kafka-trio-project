import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class WeatherStatusDto {
    private String city;
    private int temperature;
    private int chanceOfRain;

    public WeatherStatusDto() {
    }

    public WeatherStatusDto(String city, int temperature, int chanceOfRain) {
        this.city = city;
        this.temperature = temperature;
        this.chanceOfRain = chanceOfRain;
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

    @Override
    public String toString() {
        return "WeatherReportDto{" +
                "city='" + city + '\'' +
                ", temperature=" + temperature +
                ", chanceOfRain=" + chanceOfRain +
                '}';
    }
}

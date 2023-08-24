public class WeatherReportDto {

    private String city;
    private int temperature;
    private String ipAddressOfService;

    public WeatherReportDto(String city, int temperature, String ipAddressOfService) {
        this.city = city;
        this.temperature = temperature;
        this.ipAddressOfService = ipAddressOfService;
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

    public String getIpAddressOfService() {
        return ipAddressOfService;
    }

    public void setIpAddressOfService(String ipAddressOfService) {
        this.ipAddressOfService = ipAddressOfService;
    }

    @Override
    public String toString() {
        return "WeatherReportDto{" +
                "city='" + city + '\'' +
                ", temperature=" + temperature +
                ", ipAddressOfService='" + ipAddressOfService + '\'' +
                '}';
    }

    public WeatherReportDto() {
    }
}

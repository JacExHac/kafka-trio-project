public class ProcRequestDto {

    private String serviceIp;
    private String clientIp;
    private WeatherStatusDto weatherStatusDto;

    public ProcRequestDto() {
    }

    public ProcRequestDto(String serviceIp, String clientIp, WeatherStatusDto weatherStatusDto) {
        this.serviceIp = serviceIp;
        this.clientIp = clientIp;
        this.weatherStatusDto = weatherStatusDto;
    }

    public String getServiceIp() {
        return serviceIp;
    }

    public void setServiceIp(String serviceIp) {
        this.serviceIp = serviceIp;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public WeatherStatusDto getWeatherReportDto() {
        return weatherStatusDto;
    }

    public void setWeatherReportDto(WeatherStatusDto weatherStatusDto) {
        this.weatherStatusDto = weatherStatusDto;
    }

    @Override
    public String toString() {
        return "ProcRequestDto{" +
                "serviceIp='" + serviceIp + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", weatherReportDto=" + weatherStatusDto +
                '}';
    }
}
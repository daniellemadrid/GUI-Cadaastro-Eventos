public class Evento {
private String codigo;
private String data;
private double latitude;
private double longitude;

    public Evento(String codigo, String data, double latitude, double longitude) {
        this.codigo = codigo;
        this.data = data;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getData() {
        return data;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

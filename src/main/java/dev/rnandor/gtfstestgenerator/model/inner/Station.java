package dev.rnandor.gtfstestgenerator.model.inner;

import com.univocity.parsers.annotations.Parsed;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Station {

    @Parsed(field = "stop_id")
    private String id;

    @Parsed(field = "stop_name")
    private String name;

    @Parsed(field = "stop_lat")
    private double lat;

    @Parsed(field = "stop_lon")
    private double lon;


    // --- Coordinates on a 2D grid ---
    // These make it easier to place the station on the map for testing purposes
    //
    // Limitations: y must be in the range ± 10_000_000 meters
    private double x;
    private double y;


    // --- Setters ---
    public void setLat(double lat) {
        setGPS(lat, lon);
    }

    public void setLon(double lon) {
        setGPS(lat, lon);
    }

    public void setX(double x) {
        setXY(x, y);
    }

    public void setY(double y) {
        setXY(x, y);
    }

    public void setGPS(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        convertToXY();
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
        convertToGPS();
    }

    public void moveXY(double x, double y) {
        setXY(this.x + x, this.y + y);
    }

    public void moveGPS(double lat, double lon) {
        setGPS(this.lat + lat, this.lon + lon);
    }

    // --- GPS Constants ---
    private static final double M_PER_DEG_LAT = 110_574.0;
    private static final double M_PER_DEG_LON = 111_320.0;

    private void convertToGPS() {
        // latitude
        double newLat = y / M_PER_DEG_LAT;

        double latRad = Math.toRadians(newLat);
        double cosLat = Math.cos(latRad);
        double absCosLat = Math.abs(cosLat);

        if (absCosLat < 1e-12) {
            throw new IllegalStateException("cos(lat) near zero — cos(%f) = %f — longitude undefined at poles".formatted(latRad, absCosLat));
        }

        // longitude
        double newLon = (x / M_PER_DEG_LON) / cosLat;

        // normalize [-180, 180)
        newLon = ((newLon + 180.0) % 360.0);
        if (newLon < 0) newLon += 360.0;
        newLon -= 180.0;

        // mutate fields directly
        this.lat = newLat;
        this.lon = newLon;
    }

    private void convertToXY() {
        double latRad = Math.toRadians(lat);
        double cosLat = Math.cos(latRad);

        if (Math.abs(cosLat) < 1e-12) {
            throw new IllegalStateException("cos(lat) near zero — x undefined at poles");
        }

        // y = lat * meters-per-degree
        this.y = this.lat * M_PER_DEG_LAT;

        // x = lon * cos(lat) * meters-per-degree
        this.x = this.lon * cosLat * M_PER_DEG_LON;
    }
}

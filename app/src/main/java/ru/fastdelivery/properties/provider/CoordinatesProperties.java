package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.usecase.CoordinatesBorders;

@Configuration
@ConfigurationProperties("coordinates.range")
@Setter
public class CoordinatesProperties implements CoordinatesBorders {

    private Double minLatitude;
    private Double maxLatitude;
    private Double minLongitude;
    private Double maxLongitude;

    public Double minLatitude() {
        return minLatitude;
    }

    public Double maxLatitude() {
        return maxLatitude;
    }

    public Double minLongitude() {
        return minLongitude;
    }

    public Double maxLongitude() {
        return maxLongitude;
    }
}

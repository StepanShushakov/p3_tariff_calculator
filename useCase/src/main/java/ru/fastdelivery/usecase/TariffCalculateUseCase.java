package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Point;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigDecimal;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final CoordinatesBorders coordinatesBorders;

    public Price calc(Shipment shipment, Point destination, Point departure) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var volumeAllPackagesM3 = shipment.volumeAllPackages().CubicMeters();
        var minimalPrice = weightPriceProvider.minimalPrice();

        checkCoordinates(destination);
        checkCoordinates(departure);

        Price pricePerVolume =  weightPriceProvider
                .costPerCubicMeter()
                .multiply(volumeAllPackagesM3);

        Price basePrice = weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(pricePerVolume)
                .max(minimalPrice);

        double distance = calculateDistance(destination, departure) / 1000;

        if (distance < 450) {
            return basePrice;
        }

        return new Price(BigDecimal.valueOf(distance / 450).multiply(basePrice.amount()), basePrice.currency());
    }

    private void checkCoordinates(Point destination) {
        if (destination.longitude().value() > coordinatesBorders.maxLongitude()
                || destination.longitude().value() < coordinatesBorders.minLongitude()) {
            throw new IllegalArgumentException("Longitude " + destination.longitude() + " out of range!");
        }

        if (destination.latitude().value() > coordinatesBorders.maxLatitude()
                || destination.latitude().value() < coordinatesBorders.minLatitude()) {
            throw new IllegalArgumentException("Latitude " + destination.latitude() + " out of range!");
        }
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }

    public static Double calculateDistance(Point point1, Point point2) {
        // Радиус Земли в метрах
        final double EARTH_RADIUS = 6372795;

        // Преобразование градусов в радианы
        double lat1Rad = Math.toRadians(point1.latitude().value());
        double lat2Rad = Math.toRadians(point2.latitude().value());
        double long1Rad = Math.toRadians(point1.longitude().value());
        double long2Rad = Math.toRadians(point2.longitude().value());

        // Разница долгот
        double deltaLon = long2Rad - long1Rad;

        return Math.acos(Math.sin(lat1Rad) * Math.sin(lat2Rad) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.cos(deltaLon)) * EARTH_RADIUS;
    }
}

package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Point;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;

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

        return weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(pricePerVolume)
                .max(minimalPrice);
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
}

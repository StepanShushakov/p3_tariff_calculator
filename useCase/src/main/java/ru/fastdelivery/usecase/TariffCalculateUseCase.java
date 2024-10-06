package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;

    public Price calc(Shipment shipment) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var volumeAllPackagesM3 = shipment.volumeAllPackages().CubicMeters();
        var minimalPrice = weightPriceProvider.minimalPrice();

        Price pricePerVolume =  weightPriceProvider
                .costPerCubicMeter()
                .multiply(volumeAllPackagesM3);

        return weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(pricePerVolume)
                .max(minimalPrice);
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }
}

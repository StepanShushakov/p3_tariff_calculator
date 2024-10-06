package ru.fastdelivery.domain.delivery.shipment;

import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.dimensions.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigInteger;
import java.util.List;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
public record Shipment(
        List<Pack> packages,
        Currency currency
) {
    public Weight weightAllPackages() {
        return packages.stream()
                .map(Pack::weight)
                .reduce(Weight.zero(), Weight::add);
    }

    public Volume volumeAllPackages() {
        return packages.stream()
                .map(pack -> new Volume(
                        normalizeDimension(pack.length().lengthMillimeters())
                        .multiply(normalizeDimension(pack.width().widthMillimeters()))
                        .multiply(normalizeDimension(pack.height().heightMillimeters()))
                ))
                .reduce(Volume.zero(), Volume::add);
    }

    private BigInteger normalizeDimension(BigInteger dimension) {
        BigInteger factor = new BigInteger("50");
        BigInteger remainder = dimension.remainder(factor);

        if (remainder.compareTo(new BigInteger("25")) >= 0) {
            return dimension.add(factor).subtract(remainder);
        } else {
            return dimension.subtract(remainder);
        }
    }
}

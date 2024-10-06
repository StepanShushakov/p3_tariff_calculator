package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimensions.Height;
import ru.fastdelivery.domain.common.dimensions.Length;
import ru.fastdelivery.domain.common.dimensions.Width;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentTest {

    @Test
    void whenSummarizingWeightAndVolumeOfAllPackages_thenReturnSum() {
        var packages = getPacks();
        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"));

        var massOfShipment = shipment.weightAllPackages();

        assertThat(massOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));

        var totalVolume = shipment.volumeAllPackages();
        assertThat(totalVolume.volumeCubicMeters()).isEqualTo(BigInteger.valueOf(1125000));
    }

    private static List<Pack> getPacks() {
        var weight1 = new Weight(BigInteger.TEN);
        var weight2 = new Weight(BigInteger.ONE);

        var length1 = new Length(BigInteger.valueOf(55));
        var length2 = new Length(BigInteger.valueOf(95));
        var width1 = new Width(BigInteger.valueOf(45));
        var width2 = new Width(BigInteger.valueOf(110));
        var height1 = new Height(BigInteger.valueOf(64));
        var height2 = new Height(BigInteger.valueOf(120));

        return List.of(new Pack(weight1, length1, width1, height1),
                new Pack(weight2, length2, width2, height2));
    }
}
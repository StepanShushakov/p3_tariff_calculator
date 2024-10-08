package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.coordinates.Latitude;
import ru.fastdelivery.domain.common.coordinates.Longitude;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimensions.Height;
import ru.fastdelivery.domain.common.dimensions.Length;
import ru.fastdelivery.domain.common.dimensions.Width;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Point;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TariffCalculateUseCaseTest {

    final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");
    final CoordinatesBorders coordinatesBorders = mock(CoordinatesBorders.class);

    final TariffCalculateUseCase tariffCalculateUseCase = new TariffCalculateUseCase(weightPriceProvider, coordinatesBorders);

    @Test
    @DisplayName("Расчет стоимости доставки -> успешно")
    void whenCalculatePrice_thenSuccess() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var pricePerM3= new Price(BigDecimal.valueOf(5000), currency);

        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(weightPriceProvider.costPerCubicMeter()).thenReturn(pricePerM3);

        when(coordinatesBorders.minLatitude()).thenReturn(-77.1804);
        when(coordinatesBorders.maxLatitude()).thenReturn(77.1804);
        when(coordinatesBorders.minLongitude()).thenReturn(-139.55);
        when(coordinatesBorders.maxLongitude()).thenReturn(129.55);

        var shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(1200)),
                new Length(BigInteger.valueOf(364)),
                new Width(BigInteger.valueOf(674)),
                new Height(BigInteger.valueOf(1120)))),
                new CurrencyFactory(code -> true).create("RUB"));
        var expectedPrice = new Price(BigDecimal.valueOf(1251.5), currency);

        Point point1 = new Point(new Latitude(77.1539), new Longitude(120.398));
        Point point2 = new Point(new Latitude(77.1804), new Longitude(129.55));
        var actualPrice = tariffCalculateUseCase.calc(shipment, point1, point2);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }

    @Test
    @DisplayName("Получение минимальной стоимости -> успешно")
    void whenMinimalPrice_thenSuccess() {
        BigDecimal minimalValue = BigDecimal.TEN;
        var minimalPrice = new Price(minimalValue, currency);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);

        var actual = tariffCalculateUseCase.minimalPrice();

        assertThat(actual).isEqualTo(minimalPrice);
    }
}
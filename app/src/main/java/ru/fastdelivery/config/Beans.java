package ru.fastdelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.usecase.CoordinatesBorders;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.WeightPriceProvider;

/**
 * Определение реализаций бинов для всех модулей приложения
 */
@Configuration
public class Beans {

    @Bean
    public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
        return new CurrencyFactory(currencyProperties);
    }

    @Bean
    public TariffCalculateUseCase tariffCalculateUseCase(WeightPriceProvider weightPriceProvider,
                                                         @Qualifier("coordinatesProperties") CoordinatesBorders coordinatesBorders) {
        return new TariffCalculateUseCase(weightPriceProvider, coordinatesBorders);
    }
}

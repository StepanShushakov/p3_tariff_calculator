package ru.fastdelivery.properties_provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.properties.provider.CoordinatesProperties;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordinatesPropertiesTest {

    public static final Double COORDINATE = 77.400;

    CoordinatesProperties properties;

    @BeforeEach
    void init(){
        properties = new CoordinatesProperties();
        properties.setMaxLatitude(COORDINATE);
    }

    @Test
    void whenCallMaxLatitude_thenRequestFromConfig() {
        var actual = properties.maxLatitude();

        assertThat(actual).isEqualByComparingTo(COORDINATE);
    }
}

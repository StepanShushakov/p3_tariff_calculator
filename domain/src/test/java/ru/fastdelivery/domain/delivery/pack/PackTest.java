package ru.fastdelivery.domain.delivery.pack;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.dimensions.Height;
import ru.fastdelivery.domain.common.dimensions.Length;
import ru.fastdelivery.domain.common.dimensions.Width;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackTest {

    @Test
    void whenWeightMoreThanMaxWeight_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(150_001));
        assertThatThrownBy(() -> new Pack(weight, null, null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenWeightAndDimensionsLessThanMaxWeightAndDimensions_thenObjectCreated() {

        var weight = new Weight(BigInteger.valueOf(1_000));
        var length = new Length(BigInteger.valueOf(1_000));
        var width = new Width(BigInteger.valueOf(1_000));
        var height = new Height(BigInteger.valueOf(1_000));

        var actual = new Pack(weight, length, width, height);
        assertThat(actual.weight()).isEqualTo(new Weight(BigInteger.valueOf(1_000)));
        assertThat(actual.length()).isEqualTo(new Length(BigInteger.valueOf(1_000)));
        assertThat(actual.width()).isEqualTo(new Width(BigInteger.valueOf(1_000)));
        assertThat(actual.height()).isEqualTo(new Height(BigInteger.valueOf(1_000)));
    }

    @Test
    void whenLengthMoreThanMaxLength_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(4_564));
        var length = new Length(BigInteger.valueOf(1_501));
        var width = new Width(BigInteger.valueOf(1_000));
        var height = new Height(BigInteger.valueOf(1_000));
        assertThatThrownBy(() -> new Pack(weight, length, width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenWidthMoreThanMaxWidth_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(4_564));
        var length = new Length(BigInteger.valueOf(1_000));
        var width = new Width(BigInteger.valueOf(1_501));
        var height = new Height(BigInteger.valueOf(1_000));
        assertThatThrownBy(() -> new Pack(weight, length, width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenHeightMoreThanMaxHeight_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(4_564));
        var length = new Length(BigInteger.valueOf(1_000));
        var width = new Width(BigInteger.valueOf(1_000));
        var height = new Height(BigInteger.valueOf(1_501));
        assertThatThrownBy(() -> new Pack(weight, length, width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
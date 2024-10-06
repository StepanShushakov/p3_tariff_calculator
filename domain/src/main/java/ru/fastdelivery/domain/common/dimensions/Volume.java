package ru.fastdelivery.domain.common.dimensions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public record Volume(BigInteger volumeCubicMeters) {

    public static Volume zero() {
        return new Volume(BigInteger.ZERO);
    }

    public Volume add(Volume additionalVolume) {
        return new Volume(this.volumeCubicMeters.add(additionalVolume.volumeCubicMeters));
    }

    public BigDecimal CubicMeters() {
        return new BigDecimal(volumeCubicMeters)
                .divide(BigDecimal.valueOf(1_000_000_000), 4, RoundingMode.HALF_UP);
    }
}

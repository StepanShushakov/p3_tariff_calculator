package ru.fastdelivery.domain.common.dimensions;

import java.math.BigInteger;

public record Height(BigInteger heightMillimeters) implements Comparable<Height> {

    public Height {
        if (heightMillimeters == null) {
            throw new IllegalArgumentException("Height must be specified!");
        }
        if (isLessThanZero(heightMillimeters)) {
            throw new IllegalArgumentException("Height cannot be below Zero!");
        }
//        if (isMoreThan1500(heightMillimeters)) {
//            throw new IllegalArgumentException("Height cannot be more 1500!");
//        }
    }

    private static boolean isLessThanZero(BigInteger param) {
        return BigInteger.ZERO.compareTo(param) > 0;
    }

    private static boolean isMoreThan1500(BigInteger param) {
        return param.compareTo(BigInteger.valueOf(1500)) >= 0;
    }

    @Override
    public int compareTo(Height h) {
        return h.heightMillimeters.compareTo(heightMillimeters());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Height height = (Height) o;
        return heightMillimeters.compareTo(height.heightMillimeters) == 0;
    }

    public boolean greaterThan(Height h) {
        return heightMillimeters().compareTo(h.heightMillimeters()) > 0;
    }
}

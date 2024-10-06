package ru.fastdelivery.domain.common.dimensions;

import java.math.BigInteger;

public record Width(BigInteger widthMillimeters) implements Comparable<Width> {

    public Width {
        if (widthMillimeters == null) {
            throw new IllegalArgumentException("Width must be specified!");
        }
        if (isLessThanZero(widthMillimeters)) {
            throw new IllegalArgumentException("Width cannot be below Zero!");
        }
//        if (isMoreThan1500(widthMillimeters)) {
//            throw new IllegalArgumentException("Width cannot be more 1500!");
//        }
    }

    private static boolean isLessThanZero(BigInteger param) {
        return BigInteger.ZERO.compareTo(param) > 0;
    }

    private static boolean isMoreThan1500(BigInteger param) {
        return param.compareTo(BigInteger.valueOf(1500)) >= 0;
    }

    @Override
    public int compareTo(Width h) {
        return h.widthMillimeters.compareTo(widthMillimeters());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Width width = (Width) o;
        return widthMillimeters.compareTo(width.widthMillimeters) == 0;
    }

    public boolean greaterThan(Width w) {
        return widthMillimeters().compareTo(w.widthMillimeters()) > 0;
    }
}

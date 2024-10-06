package ru.fastdelivery.domain.common.dimensions;

import java.math.BigInteger;

public record Length(BigInteger lengthMillimeters) implements Comparable<Length> {

    public Length {
        if (lengthMillimeters == null) {
            throw new IllegalArgumentException("Length must be specified!");
        }
        if (isLessThanZero(lengthMillimeters)) {
            throw new IllegalArgumentException("Length cannot be below Zero!");
        }
//        if (isMoreThan1500(lengthMillimeters)) {
//            throw new IllegalArgumentException("Length cannot be more 1500!");
//        }
    }

    private static boolean isLessThanZero(BigInteger param) {
        return BigInteger.ZERO.compareTo(param) > 0;
    }

    private static boolean isMoreThan1500(BigInteger param) {
        return param.compareTo(BigInteger.valueOf(1500)) >= 0;
    }

    @Override
    public int compareTo(Length h) {
        return h.lengthMillimeters.compareTo(lengthMillimeters());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Length width = (Length) o;
        return lengthMillimeters.compareTo(width.lengthMillimeters) == 0;
    }

    public boolean greaterThan(Length l) {
            return lengthMillimeters().compareTo(l.lengthMillimeters()) > 0;
    }
}

package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.dimensions.Height;
import ru.fastdelivery.domain.common.dimensions.Length;
import ru.fastdelivery.domain.common.dimensions.Width;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 * @param length длина упаковки товара
 * @param width ширина упаковки товара
 * @param height высота упаковки товара
 */
public record Pack(Weight weight, Length length, Width width, Height height) {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));
    private static final Length maxLength = new Length(BigInteger.valueOf(1_500));
    private static final Width maxWidth = new Width(BigInteger.valueOf(1_500));
    private static final Height maxHeight = new Height(BigInteger.valueOf(1_500));

    public Pack {
        if (weight.greaterThan(maxWeight)) {
            throw new IllegalArgumentException("Package can't be more than " + maxWeight);
        }
        if (length.greaterThan(maxLength)) {
            throw new IllegalArgumentException("Length can't be more than " + maxLength);
        }
        if (width.greaterThan(maxWidth)) {
            throw new IllegalArgumentException("Width can't be more than " + maxWidth);
        }
        if (height.greaterThan(maxHeight)) {
            throw new IllegalArgumentException("Height can't be more than " + maxHeight);
        }
    }
}

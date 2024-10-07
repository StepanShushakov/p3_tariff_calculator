package ru.fastdelivery.domain.delivery.shipment;

import ru.fastdelivery.domain.common.coordinates.Latitude;
import ru.fastdelivery.domain.common.coordinates.Longitude;

public record Point (Latitude latitude, Longitude longitude){

    public Point {
    }
}

package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record Coordinates(
        @Schema(description = "широта", example = "73.398660")
        Double latitude,
        @Schema(description = "долгота", example = "65.339151")
        Double longitude) {

}

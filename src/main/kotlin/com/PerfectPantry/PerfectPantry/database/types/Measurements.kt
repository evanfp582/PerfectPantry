package com.PerfectPantry.PerfectPantry.database.types

import com.fasterxml.jackson.annotation.JsonCreator

enum class Measurements {
    TABLESPOON,
    TEASPOON,
    CUP,
    PINT,
    QUART,
    GALLON,
    POUND,
    OUNCE,
    MILLILITER,
    LITER,
    GRAM,
    SPRINKLE,
    PINCH,
    DROP,
    SLICE;

    companion object {
        @JsonCreator
        @JvmStatic
        fun from(value: String): Measurements {
            return valueOf(value.uppercase())
        }
    }
}
package dev.kaykyfreitas.video.catalog.admin.infrastructure.video.persistence;

import dev.kaykyfreitas.video.catalog.admin.domain.video.Rating;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {
    @Override
    public String convertToDatabaseColumn(final Rating rating) {
        if (rating == null) return null;
        return rating.getName();
    }

    @Override
    public Rating convertToEntityAttribute(final String s) {
        if(s == null) return null;
        return Rating.of(s).orElse(null);
    }
}

package dev.kaykyfreitas.video.catalog.admin.domain.genre;

import dev.kaykyfreitas.video.catalog.admin.domain.Identifier;
import dev.kaykyfreitas.video.catalog.admin.domain.utils.IdUtils;

import java.util.Objects;

public class GenreId extends Identifier {
    private final String value;

    private GenreId(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static GenreId unique() {
        return GenreId.from(IdUtils.uuid());
    }

    public static GenreId from(final String anId) {
        return new GenreId(anId);
    }


    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final GenreId that = (GenreId) object;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

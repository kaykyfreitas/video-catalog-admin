package dev.kaykyfreitas.video.catalog.admin.domain.genre;

import dev.kaykyfreitas.video.catalog.admin.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class GenreId extends Identifier {
    private final String value;

    private GenreId(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static GenreId unique() {
        return GenreId.from(UUID.randomUUID());
    }

    public static GenreId from(final String anId) {
        return new GenreId(anId);
    }

    public static GenreId from(final UUID anId) {
        return new GenreId(anId.toString().toLowerCase());
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

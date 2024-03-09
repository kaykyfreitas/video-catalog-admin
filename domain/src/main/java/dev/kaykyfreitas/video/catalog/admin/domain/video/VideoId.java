package dev.kaykyfreitas.video.catalog.admin.domain.video;

import dev.kaykyfreitas.video.catalog.admin.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class VideoId extends Identifier {
    private final String value;

    private VideoId(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static VideoId from(final String anId) {
        return new VideoId(anId.toLowerCase());
    }

    public static VideoId from(final UUID anId) {
        return VideoId.from(anId.toString());
    }

    public static VideoId unique() {
        return VideoId.from(UUID.randomUUID());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final VideoId that = (VideoId) object;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

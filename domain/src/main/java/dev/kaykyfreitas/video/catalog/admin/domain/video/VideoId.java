package dev.kaykyfreitas.video.catalog.admin.domain.video;

import dev.kaykyfreitas.video.catalog.admin.domain.Identifier;
import dev.kaykyfreitas.video.catalog.admin.domain.utils.IdUtils;

import java.util.Objects;

public class VideoId extends Identifier {
    private final String value;

    private VideoId(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static VideoId from(final String anId) {
        return new VideoId(anId.toLowerCase());
    }

    public static VideoId unique() {
        return VideoId.from(IdUtils.uuid());
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

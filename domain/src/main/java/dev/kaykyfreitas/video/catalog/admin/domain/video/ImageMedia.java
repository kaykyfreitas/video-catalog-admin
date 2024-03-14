package dev.kaykyfreitas.video.catalog.admin.domain.video;

import dev.kaykyfreitas.video.catalog.admin.domain.ValueObject;
import dev.kaykyfreitas.video.catalog.admin.domain.utils.IdUtils;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class ImageMedia extends ValueObject {

    private final String id;
    private final String checksum;
    private final String name;
    private final String location;

    private ImageMedia(
            final String id,
            final String checksum,
            final String name,
            final String location
    ) {
        this.id = requireNonNull(id);
        this.checksum = requireNonNull(checksum);
        this.name = requireNonNull(name);
        this.location = requireNonNull(location);
    }

    public static ImageMedia with(
            final String checksum,
            final String name,
            final String location
    ) {
        return new ImageMedia(IdUtils.uuid(), checksum, name, location);
    }

    public static ImageMedia with(
            final String id,
            final String checksum,
            final String name,
            final String location
    ) {
        return new ImageMedia(id, checksum, name, location);
    }

    public String id() {
        return this.id;
    }

    public String checksum() {
        return this.checksum;
    }

    public String name() {
        return this.name;
    }

    public String location() {
        return this.location;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ImageMedia that = (ImageMedia) o;
        return Objects.equals(checksum, that.checksum) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checksum, location);
    }

}

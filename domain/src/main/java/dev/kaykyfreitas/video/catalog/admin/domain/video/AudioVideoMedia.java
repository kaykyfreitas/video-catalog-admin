package dev.kaykyfreitas.video.catalog.admin.domain.video;

import dev.kaykyfreitas.video.catalog.admin.domain.ValueObject;
import dev.kaykyfreitas.video.catalog.admin.domain.utils.IdUtils;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class AudioVideoMedia extends ValueObject {

    private final String id;
    private final String checksum;
    private final String name;
    private final String rawLocation;
    private final String encodedLocation;
    private final MediaStatus status;

    private AudioVideoMedia(
            final String id,
            final String checksum,
            final String name,
            final String rawLocation,
            final String encodedLocation,
            final MediaStatus status
    ) {
        this.id = requireNonNull(id);
        this.checksum = requireNonNull(checksum);
        this.name = requireNonNull(name);
        this.rawLocation = requireNonNull(rawLocation);
        this.encodedLocation = requireNonNull(encodedLocation);
        this.status = requireNonNull(status);
    }

    public static AudioVideoMedia with(
            final String checksum,
            final String name,
            final String rawLocation
    ) {
        return new AudioVideoMedia(
                IdUtils.uuid(),
                checksum,
                name,
                rawLocation,
                "",
                MediaStatus.PENDING
        );
    }

    public static AudioVideoMedia with(
            final String id,
            final String checksum,
            final String name,
            final String rawLocation,
            final String encodedLocation,
            final MediaStatus status
    ) {
        return new AudioVideoMedia(
                id,
                checksum,
                name,
                rawLocation,
                encodedLocation,
                status
        );
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

    public String rawLocation() {
        return this.rawLocation;
    }

    public String encodedLocation() {
        return this.encodedLocation;
    }

    public MediaStatus status() {
        return this.status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AudioVideoMedia that = (AudioVideoMedia) o;
        return Objects.equals(checksum, that.checksum)
                && Objects.equals(rawLocation, that.rawLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checksum, rawLocation);
    }
}

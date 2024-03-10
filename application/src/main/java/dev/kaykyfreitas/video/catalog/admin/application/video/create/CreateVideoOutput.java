package dev.kaykyfreitas.video.catalog.admin.application.video.create;

import dev.kaykyfreitas.video.catalog.admin.domain.video.Video;

public record CreateVideoOutput(
        String id
) {
    public static CreateVideoOutput from(final Video aVideo) {
        return new CreateVideoOutput(aVideo.getId().getValue());
    }
}

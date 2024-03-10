package dev.kaykyfreitas.video.catalog.admin.application.video.retrieve.get;

import dev.kaykyfreitas.video.catalog.admin.domain.exceptions.NotFoundException;
import dev.kaykyfreitas.video.catalog.admin.domain.video.Video;
import dev.kaykyfreitas.video.catalog.admin.domain.video.VideoGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.video.VideoId;

import java.util.Objects;

public class DefaultGetVideoByIdUseCase extends GetVideoByIdUseCase {

    private final VideoGateway videoGateway;

    public DefaultGetVideoByIdUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public VideoOutput execute(final String anId) {
        final var aVideoId = VideoId.from(anId);
        return this.videoGateway.findById(aVideoId)
                .map(VideoOutput::from)
                .orElseThrow(() -> NotFoundException.with(Video.class, aVideoId));
    }

}

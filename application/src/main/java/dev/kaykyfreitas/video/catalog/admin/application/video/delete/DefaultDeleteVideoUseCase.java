package dev.kaykyfreitas.video.catalog.admin.application.video.delete;

import dev.kaykyfreitas.video.catalog.admin.domain.video.VideoGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.video.VideoId;

import java.util.Objects;

public class DefaultDeleteVideoUseCase extends DeleteVideoUseCase {

    private final VideoGateway videoGateway;

    public DefaultDeleteVideoUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public void execute(final String anId) {
        this.videoGateway.deleteById(VideoId.from(anId));
    }

}

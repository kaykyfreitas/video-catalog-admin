package dev.kaykyfreitas.video.catalog.admin.domain.video;

import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;

import java.util.Optional;

public interface VideoGateway {
    Video create(Video aVideo);
    void deleteById(VideoId anId);
    Optional<Video> findById(VideoId anId);
    Pagination<VideoPreview> findAll(VideoSearchQuery aQuery);
    Video update(Video aVideo);
}

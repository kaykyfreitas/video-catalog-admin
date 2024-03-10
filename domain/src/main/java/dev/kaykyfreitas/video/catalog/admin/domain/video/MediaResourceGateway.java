package dev.kaykyfreitas.video.catalog.admin.domain.video;

public interface MediaResourceGateway {
    AudioVideoMedia storeAudioVideo(VideoId anId, Resource aResource);
    ImageMedia storeImage(VideoId anId, Resource aResource);
    void clearResources(VideoId anId);
}

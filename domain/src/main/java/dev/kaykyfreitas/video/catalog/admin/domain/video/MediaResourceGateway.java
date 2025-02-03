package dev.kaykyfreitas.video.catalog.admin.domain.video;

public interface MediaResourceGateway {
    AudioVideoMedia storeAudioVideo(VideoId anId, VideoResource aResource);
    ImageMedia storeImage(VideoId anId, VideoResource aResource);
    void clearResources(VideoId anId);
}

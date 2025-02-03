package dev.kaykyfreitas.video.catalog.admin.infrastructure.video;

import dev.kaykyfreitas.video.catalog.admin.domain.resource.Resource;
import dev.kaykyfreitas.video.catalog.admin.domain.video.*;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.configuration.properties.storage.StorageProperties;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.services.StorageService;
import org.springframework.stereotype.Component;

@Component
public class DefaultMediaResourceGateway implements MediaResourceGateway {

    private final String filenamePattern;
    private final String locationPattern;
    private final StorageService storageService;

    public DefaultMediaResourceGateway(final StorageProperties props, final StorageService storageService) {
        this.filenamePattern = props.getFilenamePattern();
        this.locationPattern = props.getLocationPattern();
        this.storageService = storageService;
    }

    @Override
    public AudioVideoMedia storeAudioVideo(final VideoId anId, final VideoResource videoResource) {
        final var filepath = this.filepath(anId, videoResource);

        final var aResource = videoResource.resource();

        this.store(filepath, aResource);

        return AudioVideoMedia.with(aResource.checksum(), aResource.name(), filepath);
    }

    @Override
    public ImageMedia storeImage(final VideoId anId, final VideoResource videoResource) {
        final var filepath = this.filepath(anId, videoResource);

        final var aResource = videoResource.resource();

        this.store(filepath, aResource);

        return ImageMedia.with(aResource.checksum(), aResource.name(), filepath);
    }

    @Override
    public void clearResources(final VideoId anId) {
        final var ids = this.storageService.list(folder(anId));
        this.storageService.deleteAll(ids);
    }

    private String filename(final VideoMediaType aType) {
        return filenamePattern.replace("{type}", aType.name());
    }

    private String folder(final VideoId anId) {
        return locationPattern.replace("{videoId}", anId.getValue());
    }

    private String filepath(final VideoId anId, final VideoResource aResource) {
        return folder(anId)
                .concat("/")
                .concat(filename(aResource.type()));
    }

    private void store(final String filepath, final Resource aResource) {
        this.storageService.store(filepath, aResource);
    }

}

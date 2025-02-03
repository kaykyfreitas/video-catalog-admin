package dev.kaykyfreitas.video.catalog.admin.infrastructure.services.local;

import dev.kaykyfreitas.video.catalog.admin.domain.Fixture;
import dev.kaykyfreitas.video.catalog.admin.domain.utils.IdUtils;
import dev.kaykyfreitas.video.catalog.admin.domain.video.VideoMediaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class InMemoryStorageServiceTest {

    private final InMemoryStorageService target = new InMemoryStorageService();

    @BeforeEach
    public void setUp() {
        this.target.reset();
    }

    @Test
    public void givenAValidResource_whenCallsStore_shouldStoreIt() {
        // given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VideoMediaType.VIDEO);

        // when

        target.store(expectedName, expectedResource);

        // then
        Assertions.assertEquals(expectedResource, target.storage().get(expectedName));
    }

    @Test
    public void givenAValidResource_whenCallsGet_shouldRetrieveIt() {
        // given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VideoMediaType.VIDEO);

        target.store(expectedName, expectedResource);

        // when
        final var actualResource = target.get(expectedName).get();

        // then
        Assertions.assertEquals(expectedResource, actualResource);
    }

    @Test
    public void givenAnInvalidResource_whenCallsGet_shouldBeEmpty() {
        // given
        final var expectedName = IdUtils.uuid();

        // when
        final var actualResource = target.get(expectedName);

        // then
        Assertions.assertTrue(actualResource.isEmpty());
    }

    @Test
    public void givenAValidPrefix_whenCallsList_shouldRetrieveAll() {
        // given
        final var expectedNames = List.of(
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid()
        );

        final var all = new ArrayList<>(expectedNames);
        all.add("image_" + IdUtils.uuid());
        all.add("image_" + IdUtils.uuid());

        all.forEach(name -> target.store(name, Fixture.Videos.resource(VideoMediaType.VIDEO)));

        // when
        final var actualResource = target.list("video");

        // then
        Assertions.assertTrue(
                expectedNames.size() == actualResource.size()
                && expectedNames.containsAll(actualResource)
        );
    }

    @Test
    public void givenAValidNames_whenCallsDelete_shouldDeleteAll() {
        // given
        final var videos = List.of(
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid()
        );

        final var images = Set.of(
                "image_" + IdUtils.uuid(),
                "image_" + IdUtils.uuid()
        );

        final var all = new ArrayList<>(videos);
        all.addAll(images);

        all.forEach(name -> target.store(name, Fixture.Videos.resource(VideoMediaType.VIDEO)));

        Assertions.assertEquals(5, target.storage().size());

        // when
        target.deleteAll(videos);

        // then

        final var actualKeySet = target.storage().keySet();

        Assertions.assertTrue(
                images.size() == actualKeySet.size()
                        && images.containsAll(actualKeySet)
        );
    }

}
package dev.kaykyfreitas.video.catalog.admin.domain.video;

import dev.kaykyfreitas.video.catalog.admin.domain.utils.IdUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AudioVideoMediaTest {

    @Test
    public void givenValidParams_whenCallsNewAudioVideo_shouldReturnInstance() {
        // given
        final var expectedId = IdUtils.uuid();
        final var expectedChecksum = "abc";
        final var expectedName = "Banner.png";
        final var expectedRawLocation = "images/abc";
        final var expectedEncodedLocation = "images/abc";
        final var expectedStatus = MediaStatus.PENDING;

        // when
        final var actualVideo = AudioVideoMedia.with(
                expectedId,
                expectedChecksum,
                expectedName,
                expectedRawLocation,
                expectedEncodedLocation,
                expectedStatus
        );

        // then
        Assertions.assertNotNull(actualVideo);
        Assertions.assertEquals(expectedId, actualVideo.id());
        Assertions.assertEquals(expectedChecksum, actualVideo.checksum());
        Assertions.assertEquals(expectedName, actualVideo.name());
        Assertions.assertEquals(expectedRawLocation, actualVideo.rawLocation());
        Assertions.assertEquals(expectedEncodedLocation, actualVideo.encodedLocation());
        Assertions.assertEquals(expectedStatus, actualVideo.status());
    }

    @Test
    public void givenTwoImagesWithSameCheckSumAndLocation_whenCallsEquals_shouldReturnTrue() {
        // given
        final var expectedId = IdUtils.uuid();
        final var expectedChecksum = "abc";
        final var expectedRawLocation = "images/abc";

        final var firstAudioVideo = AudioVideoMedia.with(
                expectedId,
                expectedChecksum,
                "Random",
                expectedRawLocation,
                "/123",
                MediaStatus.PENDING
        );

        final var secondAudioVideo = AudioVideoMedia.with(
                expectedId,
                expectedChecksum,
                "Simple",
                expectedRawLocation,
                "/321",
                MediaStatus.PROCESSING
        );

        // then
        Assertions.assertEquals(firstAudioVideo, secondAudioVideo);
        Assertions.assertNotSame(firstAudioVideo, secondAudioVideo);
    }

    @Test
    public void givenInvalidParams_whenCallsWith_shouldReturnError() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with(null, "1234", "Random", "/videos", "/videos", MediaStatus.PENDING)
        );

        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with(IdUtils.uuid(), null, null, "/videos", "/videos", MediaStatus.PENDING)
        );

        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with(IdUtils.uuid(), "abc", null, "/videos", "/videos", MediaStatus.PENDING)
        );

        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with(IdUtils.uuid(), "abc", "Random", null, "/videos", MediaStatus.PENDING)
        );

        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with(IdUtils.uuid(), "abc", "Random", "/videos", null, MediaStatus.PENDING)
        );
        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with(IdUtils.uuid(), "abc", "Random", "/videos", "/videos", null)
        );
    }
}

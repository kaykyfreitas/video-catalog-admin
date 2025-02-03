package dev.kaykyfreitas.video.catalog.admin.application.video.retrieve.get;

import dev.kaykyfreitas.video.catalog.admin.application.UseCaseTest;
import dev.kaykyfreitas.video.catalog.admin.domain.Fixture;
import dev.kaykyfreitas.video.catalog.admin.domain.exceptions.NotFoundException;
import dev.kaykyfreitas.video.catalog.admin.domain.video.Video;
import dev.kaykyfreitas.video.catalog.admin.domain.video.VideoGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.video.VideoId;
import dev.kaykyfreitas.video.catalog.admin.domain.video.VideoMediaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static dev.kaykyfreitas.video.catalog.admin.domain.Fixture.Videos.audioVideo;
import static dev.kaykyfreitas.video.catalog.admin.domain.Fixture.Videos.image;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetVideoByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetVideoByIdUseCase useCase;

    @Mock
    private VideoGateway videoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(videoGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetVideo_shouldReturnIt() {
        // given
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(Fixture.Categories.aulas().getId());
        final var expectedGenres = Set.of(Fixture.Genres.tech().getId());
        final var expectedMembers = Set.of(
                Fixture.CastMembers.wesley().getId(),
                Fixture.CastMembers.gabriel().getId()
        );
        final var expectedVideo = audioVideo(VideoMediaType.VIDEO);
        final var expectedTrailer = audioVideo(VideoMediaType.TRAILER);
        final var expectedBanner = image(VideoMediaType.BANNER);
        final var expectedThumb = image(VideoMediaType.THUMBNAIL);
        final var expectedThumbHalf = image(VideoMediaType.THUMBNAIL_HALF);

        final var aVideo = Video.newVideo(
                        expectedTitle,
                        expectedDescription,
                        expectedLaunchYear,
                        expectedDuration,
                        expectedOpened,
                        expectedPublished,
                        expectedRating,
                        expectedCategories,
                        expectedGenres,
                        expectedMembers
                )
                .setVideo(expectedVideo)
                .setTrailer(expectedTrailer)
                .setBanner(expectedBanner)
                .setThumbnail(expectedThumb)
                .setThumbnailHalf(expectedThumbHalf);

        final var expectedId = aVideo.getId();

        when(videoGateway.findById(any()))
                .thenReturn(Optional.of(Video.with(aVideo)));

        // when
        final var actualVideo = this.useCase.execute(expectedId.getValue());

        // then
        Assertions.assertEquals(expectedId.getValue(), actualVideo.id());
        Assertions.assertEquals(expectedTitle, actualVideo.title());
        Assertions.assertEquals(expectedDescription, actualVideo.description());
        Assertions.assertEquals(expectedLaunchYear.getValue(), actualVideo.launchedAt());
        Assertions.assertEquals(expectedDuration, actualVideo.duration());
        Assertions.assertEquals(expectedOpened, actualVideo.opened());
        Assertions.assertEquals(expectedPublished, actualVideo.published());
        Assertions.assertEquals(expectedRating, actualVideo.rating());
        Assertions.assertEquals(asString(expectedCategories), actualVideo.categories());
        Assertions.assertEquals(asString(expectedGenres), actualVideo.genres());
        Assertions.assertEquals(asString(expectedMembers), actualVideo.castMembers());
        Assertions.assertEquals(expectedVideo, actualVideo.video());
        Assertions.assertEquals(expectedTrailer, actualVideo.trailer());
        Assertions.assertEquals(expectedBanner, actualVideo.banner());
        Assertions.assertEquals(expectedThumb, actualVideo.thumbnail());
        Assertions.assertEquals(expectedThumbHalf, actualVideo.thumbnailHalf());
        Assertions.assertEquals(aVideo.getCreatedAt(), actualVideo.createdAt());
        Assertions.assertEquals(aVideo.getUpdatedAt(), actualVideo.updatedAt());
    }

    @Test
    public void givenInvalidId_whenCallsGetVideo_shouldReturnNotFound() {
        // given
        final var expectedErrorMessage = "video with id 123 was not found";

        final var expectedId = VideoId.from("123");

        when(videoGateway.findById(any()))
                .thenReturn(Optional.empty());

        // when
        final var actualError = Assertions.assertThrows(
                NotFoundException.class,
                () -> this.useCase.execute(expectedId.getValue())
        );

        // then
        Assertions.assertEquals(expectedErrorMessage, actualError.getMessage());
    }
}

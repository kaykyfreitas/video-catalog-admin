package dev.kaykyfreitas.video.catalog.admin.application.video.delete;

import dev.kaykyfreitas.video.catalog.admin.application.UseCaseTest;
import dev.kaykyfreitas.video.catalog.admin.domain.exceptions.InternalErrorException;
import dev.kaykyfreitas.video.catalog.admin.domain.video.VideoGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.video.VideoId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DeleteVideoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteVideoUseCase useCase;

    @Mock
    private VideoGateway videoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(videoGateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteVideo_shouldDeleteIt() {
        // given
        final var expectedId = VideoId.unique();

        doNothing()
                .when(videoGateway).deleteById(any());

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        // then
        verify(videoGateway).deleteById(eq(expectedId));
    }

    @Test
    public void givenAaInvalidId_whenCallsDeleteVideo_shouldBeOk() {
        // given
        final var expectedId = VideoId.from("123");

        doNothing()
                .when(videoGateway).deleteById(any());

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        // then
        verify(videoGateway).deleteById(eq(expectedId));
    }

    @Test
    public void givenAaValidId_whenCallsDeleteVideoAndGatewayThrowsAnException_shouldReceiveException() {
        // given
        final var expectedId = VideoId.from("123");

        doThrow(InternalErrorException.with("Error on delete video", new RuntimeException()))
                .when(videoGateway).deleteById(any());

        // when
        Assertions.assertThrows(
                InternalErrorException.class,
                () -> this.useCase.execute(expectedId.getValue())
        );

        // then
        verify(videoGateway).deleteById(eq(expectedId));
    }

}

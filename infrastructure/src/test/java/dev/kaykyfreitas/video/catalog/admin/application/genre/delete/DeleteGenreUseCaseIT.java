package dev.kaykyfreitas.video.catalog.admin.application.genre.delete;

import dev.kaykyfreitas.video.catalog.admin.IntegrationTest;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.Genre;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.GenreGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.GenreId;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class DeleteGenreUseCaseIT {

    @Autowired
    private DeleteGenreUseCase useCase;

    @Autowired
    private GenreGateway genreGateway;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenre_shouldDeleteGenre() {
        // given
        final var aGenre = genreGateway.create(Genre.newGenre("Ação", true));

        final var expectedId = aGenre.getId();

        Assertions.assertEquals(1, genreRepository.count());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // when
        Assertions.assertEquals(0, genreRepository.count());
    }

    @Test
    public void givenAnInvalidGenreId_whenCallsDeleteGenre_shouldBeOk() {
        // given
        genreGateway.create(Genre.newGenre("Ação", true));

        final var expectedId = GenreId.from("123");

        Assertions.assertEquals(1, genreRepository.count());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // when
        Assertions.assertEquals(1, genreRepository.count());
    }

}

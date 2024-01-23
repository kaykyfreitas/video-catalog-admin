package dev.kaykyfreitas.video.catalog.admin.application.genre.retrieve.list;

import dev.kaykyfreitas.video.catalog.admin.application.UseCaseTest;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.Genre;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.GenreGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class ListGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of((genreGateway));
    }

    @Test
    public void givenAValidQuery_whenCallsListGenre_shouldReturnGenre() {
        // given
        final var genres = List.of(
                Genre.newGenre("Ação", true),
                Genre.newGenre("Aventura", true)
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "A";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 2;

        final var expectedItems = genres.stream()
                .map(GenreListOutput::from)
                .toList();

        final var expectedPagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                genres
        );

        when(genreGateway.findAll(any()))
                .thenReturn(expectedPagination);

        final var aQuey =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualOutput = useCase.execute(aQuey);

        // then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertEquals(expectedItems, actualOutput.items());

        Mockito.verify(genreGateway, times(1)).findAll(eq(aQuey));
    }

    @Test
    public void givenAValidQuery_whenCallsListGenreAndResultIsEmpty_shouldReturnGenre() {
        // given
        final var genres = List.<Genre>of();

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "A";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var expectedItems = List.<GenreListOutput>of();

        final var expectedPagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                genres
        );

        when(genreGateway.findAll(any()))
                .thenReturn(expectedPagination);

        final var aQuey =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualOutput = useCase.execute(aQuey);

        // then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertEquals(expectedItems, actualOutput.items());

        Mockito.verify(genreGateway, times(1)).findAll(eq(aQuey));
    }

    @Test
    public void givenAValidQuery_whenCallsListGenreAndGatewayThrowsAnError_shouldReturnAnExceptionGenre() {
        // given
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "A";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";

        final var gatewayErrorMessage = "gateway error";

        when(genreGateway.findAll(any()))
                .thenThrow(new IllegalStateException(gatewayErrorMessage));

        final var aQuey =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualOutput = Assertions.assertThrows(
                IllegalStateException.class, () ->
                        useCase.execute(aQuey)
        );

        // then
        Assertions.assertEquals(gatewayErrorMessage, actualOutput.getMessage());

        Mockito.verify(genreGateway, times(1)).findAll(eq(aQuey));
    }

}

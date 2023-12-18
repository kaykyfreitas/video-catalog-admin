package dev.kaykyfreitas.video.catalog.admin.application.genre.create;

import dev.kaykyfreitas.video.catalog.admin.application.UseCaseTest;
import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryId;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.GenreGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CreateGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateGenreUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway, genreGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateGenre_shouldReturnGenreId() {
        // given
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryId>of();

        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        when(genreGateway.create(any())).thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(genreGateway, times(1)).create(argThat(aGenre ->
                Objects.equals(expectedName, aGenre.getName())
                        && Objects.equals(expectedIsActive, aGenre.isActive())
                        && Objects.equals(expectedCategories, aGenre.getCategories())
                        && Objects.nonNull(aGenre.getId())
                        && Objects.nonNull(aGenre.getCreatedAt())
                        && Objects.nonNull(aGenre.getUpdatedAt())
                        && Objects.isNull(aGenre.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommandWithCategories_whenCallsCreateGenre_shouldReturnGenreId() {
        // given
        final var expectName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                CategoryId.from("123"),
                CategoryId.from("456")
        );

        final var aCommand =
                CreateGenreCommand.with(expectName, expectedIsActive, asString(expectedCategories));

        when(categoryGateway.existsByIds(any()))
                .thenReturn(expectedCategories);

        when(genreGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).existsByIds(expectedCategories);

        Mockito.verify(genreGateway, times(1)).create(argThat(aGenre ->
                Objects.equals(expectName, aGenre.getName())
                        && Objects.equals(expectedIsActive, aGenre.isActive())
                        && Objects.equals(expectedCategories, aGenre.getCategories())
                        && Objects.nonNull(aGenre.getId())
                        && Objects.nonNull(aGenre.getCreatedAt())
                        && Objects.nonNull(aGenre.getUpdatedAt())
                        && Objects.isNull(aGenre.getDeletedAt())
        ));
    }

    private List<String> asString(List<CategoryId> categories) {
        return categories.stream()
                .map(CategoryId::getValue)
                .toList();
    }
}

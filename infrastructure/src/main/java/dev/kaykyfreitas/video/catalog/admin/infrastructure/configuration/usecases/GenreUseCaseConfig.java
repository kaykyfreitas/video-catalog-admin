package dev.kaykyfreitas.video.catalog.admin.infrastructure.configuration.usecases;

import dev.kaykyfreitas.video.catalog.admin.application.genre.create.CreateGenreUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.genre.create.DefaultCreateGenreUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.genre.delete.DefaultDeleteGenreUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.genre.delete.DeleteGenreUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.genre.retrieve.get.DefaultGetGenreByIdUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.genre.retrieve.get.GetGenreByIdUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.genre.retrieve.list.DefaultListGenreUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.genre.retrieve.list.ListGenreUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.genre.update.DefaultUpdateGenreUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.genre.update.UpdateGenreUseCase;
import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.GenreGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class GenreUseCaseConfig {

    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;

    public GenreUseCaseConfig(
            final CategoryGateway categoryGateway,
            final GenreGateway genreGateway
    ) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Bean
    public CreateGenreUseCase createGenreUseCase() {
        return new DefaultCreateGenreUseCase(
                categoryGateway,
                genreGateway
        );
    }

    @Bean
    public DeleteGenreUseCase deleteGenreUseCase() {
        return new DefaultDeleteGenreUseCase(genreGateway);
    }

    @Bean
    public GetGenreByIdUseCase getGenreByIdUseCase() {
        return new DefaultGetGenreByIdUseCase(genreGateway);
    }

    @Bean
    public ListGenreUseCase listGenreUseCase() {
        return new DefaultListGenreUseCase(genreGateway);
    }

    @Bean
    public UpdateGenreUseCase updateGenreUseCase() {
        return new DefaultUpdateGenreUseCase(
                categoryGateway,
                genreGateway
        );
    }

}

package dev.kaykyfreitas.video.catalog.admin.infrastructure.genre;

import dev.kaykyfreitas.video.catalog.admin.domain.genre.Genre;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.GenreGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.GenreId;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.SearchQuery;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.genre.persistence.GenreJpaEntity;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.genre.persistence.GenreRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class GenreMySQLGateway implements GenreGateway {

    private final GenreRepository genreRepository;

    public GenreMySQLGateway(final GenreRepository genreRepository) {
        this.genreRepository = Objects.requireNonNull(genreRepository);
    }

    @Override
    public Genre create(Genre aGenre) {
        return save(aGenre);
    }

    @Override
    public void deleteById(GenreId anId) {

    }

    @Override
    public Optional<Genre> findById(GenreId anId) {
        return Optional.empty();
    }

    @Override
    public Genre update(Genre aGenre) {
        return save(aGenre);
    }

    @Override
    public Pagination<Genre> findAll(SearchQuery aQuery) {
        return null;
    }

    private Genre save(Genre aGenre) {
        return this.genreRepository.save(GenreJpaEntity.from(aGenre)).toAggregate();
    }
}

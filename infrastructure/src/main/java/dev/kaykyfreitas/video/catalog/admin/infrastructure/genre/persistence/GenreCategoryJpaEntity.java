package dev.kaykyfreitas.video.catalog.admin.infrastructure.genre.persistence;

import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "genres_categories")
public class GenreCategoryJpaEntity {

    @EmbeddedId
    private GenreCategoryId id;

    @ManyToOne
    @MapsId("genreId")
    private GenreJpaEntity genre;

    public GenreCategoryJpaEntity() {}

    private GenreCategoryJpaEntity(final GenreJpaEntity aGenre, final CategoryId aCategoryId) {
        this.id = GenreCategoryId.from(aGenre.getId(), aCategoryId.getValue());
        this.genre = aGenre;
    }

    public static GenreCategoryJpaEntity from(final GenreJpaEntity aGenre, final CategoryId aCategoryId) {
        return new GenreCategoryJpaEntity(aGenre, aCategoryId);
    }

    public GenreCategoryId getId() {
        return id;
    }

    public void setId(final GenreCategoryId anId) {
        this.id = anId;
    }

    public GenreJpaEntity getGenre() {
        return genre;
    }

    public void setGenre(final GenreJpaEntity aGenre) {
        this.genre = aGenre;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GenreCategoryJpaEntity that = (GenreCategoryJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

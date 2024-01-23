package dev.kaykyfreitas.video.catalog.admin.infrastructure.genre.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GenreCategoryId implements Serializable {

    @Column(name = "genre_id", nullable = false)
    private String genreId;

    @Column(name = "category_id", nullable = false)
    private String categoryId;

    public GenreCategoryId() {}

    private GenreCategoryId(final String aGenreId, final String aCategoryId) {
        this.genreId = aGenreId;
        this.categoryId = aCategoryId;
    }

    public static GenreCategoryId from(final String aGenreId, final String aCategoryId) {
        return new GenreCategoryId(aGenreId, aCategoryId);
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(final String aGenreId) {
        this.genreId = aGenreId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final String aCategoryId) {
        this.categoryId = aCategoryId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GenreCategoryId that = (GenreCategoryId) o;
        return Objects.equals(getGenreId(), that.getGenreId())
                && Objects.equals(getCategoryId(), that.getCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGenreId(), getCategoryId());
    }
}

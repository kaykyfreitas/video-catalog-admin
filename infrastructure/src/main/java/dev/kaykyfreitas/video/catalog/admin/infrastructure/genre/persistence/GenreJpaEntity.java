package dev.kaykyfreitas.video.catalog.admin.infrastructure.genre.persistence;

import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryId;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.Genre;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.GenreId;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "genres")
public class GenreJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "genre", cascade = ALL, fetch = EAGER, orphanRemoval = true)
    private Set<GenreCategoryJpaEntity> categories;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @Column(name = "deleted_at", columnDefinition = "DATETIME(6)")
    private Instant deletedAt;

    public GenreJpaEntity() {}

    private GenreJpaEntity(
            final String anId,
            final String aName,
            final boolean isActive,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        this.id = anId;
        this.name = aName;
        this.active = isActive;
        this.categories = new HashSet<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static GenreJpaEntity from(final Genre aGenre) {
        final var anEntity = new GenreJpaEntity(
                aGenre.getId().getValue(),
                aGenre.getName(),
                aGenre.isActive(),
                aGenre.getCreatedAt(),
                aGenre.getUpdatedAt(),
                aGenre.getDeletedAt()
        );

        aGenre.getCategories().forEach(anEntity::addCategory);

        return anEntity;
    }

    public Genre toAggregate() {
        return Genre.with(
                GenreId.from(this.getId()),
                this.getName(),
                this.isActive(),
                getCategoriesIds(),
                this.getCreatedAt(),
                this.getUpdatedAt(),
                this.getDeletedAt()
        );
    }

    private void addCategory(final CategoryId anId) {
        this.categories.add(GenreCategoryJpaEntity.from(this, anId));
    }

    private void removeCategory(final CategoryId anId) {
        this.categories.remove(GenreCategoryJpaEntity.from(this, anId));
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public List<CategoryId> getCategoriesIds() {
        return this.categories.stream()
                .map(it -> CategoryId.from(it.getId().getCategoryId()))
                .toList();
    }

    public Set<GenreCategoryJpaEntity> getCategories() {
        return categories;
    }

    public void setCategories(final Set<GenreCategoryJpaEntity> categories) {
        this.categories = categories;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(final Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
}

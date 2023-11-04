package dev.kaykyfreitas.video.catalog.admin.domain.genre;

import dev.kaykyfreitas.video.catalog.admin.domain.AggregateRoot;
import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryId;
import dev.kaykyfreitas.video.catalog.admin.domain.exceptions.NotificationException;
import dev.kaykyfreitas.video.catalog.admin.domain.utils.InstantUtils;
import dev.kaykyfreitas.video.catalog.admin.domain.validation.ValidationHandler;
import dev.kaykyfreitas.video.catalog.admin.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Genre extends AggregateRoot<GenreId> {

    private String name;
    private boolean active;
    private List<CategoryId> categories;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    protected Genre(
            final GenreId anId,
            final String aName,
            final boolean isActive,
            final List<CategoryId> categories,
            final Instant aCreatedAt,
            final Instant anUpdatedAt,
            final Instant aDeletedAt
    ) {
        super(anId);
        this.name = aName;
        this.categories = categories;
        this.active = isActive;
        this.createdAt = aCreatedAt;
        this.updatedAt = anUpdatedAt;
        this.deletedAt = aDeletedAt;
        selfValidate();
    }

    public static Genre newGenre(final String aName, final boolean isActive) {
        final var anId = GenreId.unique();
        final var now = InstantUtils.now();
        final var deletedAt = isActive ? null : now;
        return new Genre(anId, aName, isActive, new ArrayList<>(), now, now, deletedAt);
    }

    public static Genre with(
            final GenreId anId,
            final String aName,
            final boolean isActive,
            final List<CategoryId> categories,
            final Instant aCreatedAt,
            final Instant anUpdatedAt,
            final Instant aDeletedAt
    ) {
        return new Genre(anId, aName, isActive, categories, aCreatedAt, anUpdatedAt, aDeletedAt);
    }

    public static Genre with(final Genre aGenre) {
        return new Genre(
                aGenre.id,
                aGenre.name,
                aGenre.active,
                new ArrayList<>(aGenre.categories),
                aGenre.createdAt,
                aGenre.updatedAt,
                aGenre.deletedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new GenreValidator(this, handler).validate();
    }

    public Genre update(final String aName, final boolean isActive, final List<CategoryId> categories) {
        if (isActive)
            activate();
        else
            deactivate();

        this.name = aName;
        this.categories = new ArrayList<>(categories != null ? categories : Collections.emptyList());
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    public Genre addCategory(final CategoryId aCategoryId) {
        if (aCategoryId == null)
            return this;

        this.categories.add(aCategoryId);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre addCategories(final List<CategoryId> categories) {
        if (categories == null || categories.isEmpty())
            return this;

        this.categories.addAll(categories);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre removeCategory(final CategoryId aCategoryid) {
        if (aCategoryid == null)
            return this;

        this.categories.remove(aCategoryid);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre deactivate() {
        if (Objects.isNull(this.deletedAt))
            this.deletedAt = InstantUtils.now();

        this.active = false;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public List<CategoryId> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("failed to create an aggregate genre", notification);
        }
    }
}

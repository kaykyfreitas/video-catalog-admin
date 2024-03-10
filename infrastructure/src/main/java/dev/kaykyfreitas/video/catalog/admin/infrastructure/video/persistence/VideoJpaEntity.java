package dev.kaykyfreitas.video.catalog.admin.infrastructure.video.persistence;

import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberId;
import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryId;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.GenreId;
import dev.kaykyfreitas.video.catalog.admin.domain.video.Rating;
import dev.kaykyfreitas.video.catalog.admin.domain.video.Video;
import dev.kaykyfreitas.video.catalog.admin.domain.video.VideoId;

import javax.persistence.*;
import java.time.Instant;
import java.time.Year;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "Video")
@Table(name = "videos")
public class VideoJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "year_launched", nullable = false)
    private int yearLaunched;

    @Column(name = "opened", nullable = false)
    private boolean opened;

    @Column(name = "published", nullable = false)
    private boolean published;

    @Column(name = "rating")
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column(name = "duration", precision = 2)
    private double duration;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "video_id")
    private AudioVideoMediaJpaEntity video;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "trailer_id")
    private AudioVideoMediaJpaEntity trailer;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "banner_id")
    private ImageMediaJpaEntity banner;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "thumbnail_id")
    private ImageMediaJpaEntity thumbnail;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "thumbnail_half_id")
    private ImageMediaJpaEntity thumbnailHalf;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VideoCategoryJpaEntity> categories;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VideoGenreJpaEntity> genres;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VideoCastMemberJpaEntity> castMembers;


    public VideoJpaEntity() {
    }

    private VideoJpaEntity(
            final String id,
            final String title,
            final String description,
            final int yearLaunched,
            final boolean opened,
            final boolean published,
            final Rating rating,
            final double duration,
            final Instant createdAt,
            final Instant updatedAt,
            final AudioVideoMediaJpaEntity video,
            final AudioVideoMediaJpaEntity trailer,
            final ImageMediaJpaEntity banner,
            final ImageMediaJpaEntity thumbnail,
            final ImageMediaJpaEntity thumbnailHalf
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.yearLaunched = yearLaunched;
        this.opened = opened;
        this.published = published;
        this.rating = rating;
        this.duration = duration;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.video = video;
        this.trailer = trailer;
        this.banner = banner;
        this.thumbnail = thumbnail;
        this.thumbnailHalf = thumbnailHalf;
        this.categories = new HashSet<>(3);
        this.genres = new HashSet<>(3);
        this.castMembers = new HashSet<>(3);
    }

    public static VideoJpaEntity from(final Video aVideo) {
        final var entity = new VideoJpaEntity(
                aVideo.getId().getValue(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getLaunchedAt().getValue(),
                aVideo.getOpened(),
                aVideo.getPublished(),
                aVideo.getRating(),
                aVideo.getDuration(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt(),
                aVideo.getVideo()
                        .map(AudioVideoMediaJpaEntity::from)
                        .orElse(null),
                aVideo.getTrailer()
                        .map(AudioVideoMediaJpaEntity::from)
                        .orElse(null),
                aVideo.getBanner()
                        .map(ImageMediaJpaEntity::from)
                        .orElse(null),
                aVideo.getThumbnail()
                        .map(ImageMediaJpaEntity::from)
                        .orElse(null),
                aVideo.getThumbnailHalf()
                        .map(ImageMediaJpaEntity::from)
                        .orElse(null)
        );

        aVideo.getCategories()
                .forEach(entity::addCategory);

        aVideo.getGenres()
                .forEach(entity::addGenre);

        aVideo.getCastMembers()
                .forEach(entity::addCastMember);

        return entity;
    }

    public void addCategory(final CategoryId anId) {
        this.categories.add(VideoCategoryJpaEntity.from(this, anId));
    }

    public void addGenre(final GenreId anId) {
        this.genres.add(VideoGenreJpaEntity.from(this, anId));
    }

    public void addCastMember(final CastMemberId anId) {
        this.castMembers.add(VideoCastMemberJpaEntity.from(this, anId));
    }

    public Video toAggregate() {
        return Video.with(
                VideoId.from(this.getId()),
                this.getTitle(),
                this.getDescription(),
                Year.of(this.getYearLaunched()),
                this.getDuration(),
                this.isOpened(),
                this.isPublished(),
                this.getRating(),
                this.getCreatedAt(),
                this.getUpdatedAt(),
                Optional.ofNullable(this.getBanner())
                        .map(ImageMediaJpaEntity::toDomain)
                        .orElse(null),
                Optional.ofNullable(this.getThumbnail())
                        .map(ImageMediaJpaEntity::toDomain)
                        .orElse(null),
                Optional.ofNullable(this.getThumbnailHalf())
                        .map(ImageMediaJpaEntity::toDomain)
                        .orElse(null),
                Optional.ofNullable(this.getTrailer())
                        .map(AudioVideoMediaJpaEntity::toDomain)
                        .orElse(null),
                Optional.ofNullable(this.getVideo())
                        .map(AudioVideoMediaJpaEntity::toDomain)
                        .orElse(null),
                this.getCategories().stream()
                        .map(it -> CategoryId.from(it.getId().getCategoryId()))
                        .collect(Collectors.toSet()),
                this.getGenres().stream()
                        .map(it -> GenreId.from(it.getId().getGenreId()))
                        .collect(Collectors.toSet()),
                this.getCastMembers().stream()
                        .map(it -> CastMemberId.from(it.getId().getCastMemberId()))
                        .collect(Collectors.toSet())
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYearLaunched() {
        return yearLaunched;
    }

    public void setYearLaunched(int yearLaunched) {
        this.yearLaunched = yearLaunched;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AudioVideoMediaJpaEntity getVideo() {
        return video;
    }

    public void setVideo(AudioVideoMediaJpaEntity video) {
        this.video = video;
    }

    public AudioVideoMediaJpaEntity getTrailer() {
        return trailer;
    }

    public void setTrailer(AudioVideoMediaJpaEntity trailer) {
        this.trailer = trailer;
    }

    public ImageMediaJpaEntity getBanner() {
        return banner;
    }

    public void setBanner(ImageMediaJpaEntity banner) {
        this.banner = banner;
    }

    public ImageMediaJpaEntity getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageMediaJpaEntity thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ImageMediaJpaEntity getThumbnailHalf() {
        return thumbnailHalf;
    }

    public void setThumbnailHalf(ImageMediaJpaEntity thumbnailHalf) {
        this.thumbnailHalf = thumbnailHalf;
    }

    public Set<VideoCategoryJpaEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<VideoCategoryJpaEntity> categories) {
        this.categories = categories;
    }

    public Set<VideoGenreJpaEntity> getGenres() {
        return genres;
    }

    public void setGenres(Set<VideoGenreJpaEntity> genres) {
        this.genres = genres;
    }

    public Set<VideoCastMemberJpaEntity> getCastMembers() {
        return castMembers;
    }

    public void setCastMembers(Set<VideoCastMemberJpaEntity> castMembers) {
        this.castMembers = castMembers;
    }
}

package dev.kaykyfreitas.video.catalog.admin.infrastructure.video.persistence;

import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryId;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "videos_categories")
@Entity(name = "VideoCategory")
public class VideoCategoryJpaEntity {

    @EmbeddedId
    private VideoCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("videoId")
    private VideoJpaEntity video;

    public VideoCategoryJpaEntity() {
    }

    private VideoCategoryJpaEntity(final VideoCategoryId id, final VideoJpaEntity video) {
        this.id = id;
        this.video = video;
    }

    public static VideoCategoryJpaEntity from(final VideoJpaEntity video, final CategoryId categoryId) {
        return new VideoCategoryJpaEntity(
                VideoCategoryId.from(video.getId(), categoryId.getValue()),
                video
        );
    }

    public VideoCategoryId getId() {
        return id;
    }

    public void setId(VideoCategoryId id) {
        this.id = id;
    }

    public VideoJpaEntity getVideo() {
        return video;
    }

    public void setVideo(VideoJpaEntity video) {
        this.video = video;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VideoCategoryJpaEntity that = (VideoCategoryJpaEntity) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getVideo(), that.getVideo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVideo());
    }
}

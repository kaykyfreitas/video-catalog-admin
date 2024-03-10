package dev.kaykyfreitas.video.catalog.admin.infrastructure.video.persistence;

import dev.kaykyfreitas.video.catalog.admin.domain.genre.GenreId;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "VideoGenre")
@Table(name = "videos_genres")
public class VideoGenreJpaEntity {

    @EmbeddedId
    private VideoGenreId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("videoId")
    private VideoJpaEntity video;

    public VideoGenreJpaEntity() {
    }

    private VideoGenreJpaEntity(final VideoGenreId id, final VideoJpaEntity video) {
        this.id = id;
        this.video = video;
    }

    public static VideoGenreJpaEntity from(final VideoJpaEntity video, final GenreId genreId) {
        return new VideoGenreJpaEntity(
                VideoGenreId.from(video.getId(), genreId.getValue()),
                video
        );
    }

    public VideoGenreId getId() {
        return id;
    }

    public void setId(VideoGenreId id) {
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
        final VideoGenreJpaEntity that = (VideoGenreJpaEntity) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getVideo(), that.getVideo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVideo());
    }
}

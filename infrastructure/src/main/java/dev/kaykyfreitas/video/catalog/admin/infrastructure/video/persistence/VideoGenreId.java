package dev.kaykyfreitas.video.catalog.admin.infrastructure.video.persistence;

import dev.kaykyfreitas.video.catalog.admin.domain.video.VideoId;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class VideoGenreId implements Serializable {

    @Column(name = "video_id", nullable = false)
    private UUID videoId;

    @Column(name = "genre_id", nullable = false)
    private UUID genreId;

    public VideoGenreId() {
    }

    private VideoGenreId(final UUID videoId, final UUID genreId) {
        this.videoId = videoId;
        this.genreId = genreId;
    }

    public static VideoGenreId from(final UUID videoId, final UUID genreId) {
        return new VideoGenreId(videoId, genreId);
    }

    public UUID getVideoId() {
        return videoId;
    }

    public void setVideoId(UUID videoId) {
        this.videoId = videoId;
    }

    public UUID getGenreId() {
        return genreId;
    }

    public void setGenreId(UUID genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VideoGenreId that = (VideoGenreId) o;
        return Objects.equals(getVideoId(), that.getVideoId())
                && Objects.equals(getGenreId(), that.getGenreId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVideoId(), getGenreId());
    }
}

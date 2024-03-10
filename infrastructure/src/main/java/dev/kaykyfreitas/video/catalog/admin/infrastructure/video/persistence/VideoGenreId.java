package dev.kaykyfreitas.video.catalog.admin.infrastructure.video.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VideoGenreId implements Serializable {

    @Column(name = "video_id", nullable = false)
    private String videoId;

    @Column(name = "genre_id", nullable = false)
    private String genreId;

    public VideoGenreId() {
    }

    private VideoGenreId(final String videoId, final String genreId) {
        this.videoId = videoId;
        this.genreId = genreId;
    }

    public static VideoGenreId from(final String videoId, final String genreId) {
        return new VideoGenreId(videoId, genreId);
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
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

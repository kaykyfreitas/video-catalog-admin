package dev.kaykyfreitas.video.catalog.admin.infrastructure.video.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VideoCategoryId implements Serializable {

    @Column(name = "video_id", nullable = false)
    private String videoId;

    @Column(name = "category_id", nullable = false)
    private String categoryId;

    public VideoCategoryId() {
    }

    private VideoCategoryId(final String videoId, final String categoryId) {
        this.videoId = videoId;
        this.categoryId = categoryId;
    }

    public static VideoCategoryId from(final String videoId, final String categoryId) {
        return new VideoCategoryId(videoId, categoryId);
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VideoCategoryId that = (VideoCategoryId) o;
        return Objects.equals(getVideoId(), that.getVideoId())
                && Objects.equals(getCategoryId(), that.getCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVideoId(), getCategoryId());
    }
}

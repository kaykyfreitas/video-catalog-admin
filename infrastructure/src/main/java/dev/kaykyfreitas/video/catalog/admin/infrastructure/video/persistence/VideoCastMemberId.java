package dev.kaykyfreitas.video.catalog.admin.infrastructure.video.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VideoCastMemberId implements Serializable {

    @Column(name = "video_id", nullable = false)
    private String videoId;

    @Column(name = "cast_member_id", nullable = false)
    private String castMemberId;

    public VideoCastMemberId() {
    }

    private VideoCastMemberId(final String videoId, final String castMemberId) {
        this.videoId = videoId;
        this.castMemberId = castMemberId;
    }

    public static VideoCastMemberId from(final String videoId, final String castMemberId) {
        return new VideoCastMemberId(videoId, castMemberId);
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getCastMemberId() {
        return castMemberId;
    }

    public void setCastMemberId(String castMemberId) {
        this.castMemberId = castMemberId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VideoCastMemberId that = (VideoCastMemberId) o;
        return Objects.equals(getVideoId(), that.getVideoId())
                && Objects.equals(getCastMemberId(), that.getCastMemberId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVideoId(), getCastMemberId());
    }
}

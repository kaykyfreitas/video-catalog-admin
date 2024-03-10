package dev.kaykyfreitas.video.catalog.admin.infrastructure.video.persistence;

import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberId;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Table(name = "videos_cast_members")
@Entity(name = "VideoCastMember")
public class VideoCastMemberJpaEntity {

    @EmbeddedId
    private VideoCastMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("videoId")
    private VideoJpaEntity video;

    public VideoCastMemberJpaEntity() {
    }

    private VideoCastMemberJpaEntity(final VideoCastMemberId id, final VideoJpaEntity video) {
        this.id = id;
        this.video = video;
    }

    public static VideoCastMemberJpaEntity from(final VideoJpaEntity video, final CastMemberId castMemberId) {
        return new VideoCastMemberJpaEntity(
                VideoCastMemberId.from(video.getId(), UUID.fromString(castMemberId.getValue())),
                video
        );
    }

    public VideoCastMemberId getId() {
        return id;
    }

    public void setId(VideoCastMemberId id) {
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
        final VideoCastMemberJpaEntity that = (VideoCastMemberJpaEntity) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getVideo(), that.getVideo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVideo());
    }
}

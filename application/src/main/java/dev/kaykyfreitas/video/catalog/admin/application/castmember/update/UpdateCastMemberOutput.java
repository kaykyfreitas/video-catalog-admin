package dev.kaykyfreitas.video.catalog.admin.application.castmember.update;

import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMember;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberId;

public record UpdateCastMemberOutput(
        String id
) {
    public static UpdateCastMemberOutput from(final CastMemberId aMemberId) {
        return new UpdateCastMemberOutput(aMemberId.getValue());
    }
    public static UpdateCastMemberOutput from(final CastMember aMember) {
        return from(aMember.getId());
    }
}

package dev.kaykyfreitas.video.catalog.admin.application.castmember.update;

import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMember;

public record UpdateCastMemberOutput(
        String id
) {
    public static UpdateCastMemberOutput from(final CastMember aMember) {
        return new UpdateCastMemberOutput(aMember.getId().getValue());
    }
}

package dev.kaykyfreitas.video.catalog.admin.application.castmember.create;

import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMember;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberId;

public record CreateCastMemberOutput(
        String id
) {
    public static CreateCastMemberOutput from(final CastMemberId aMemberId) {
        return new CreateCastMemberOutput(aMemberId.getValue());
    }
    public static CreateCastMemberOutput from(final CastMember aMember) {
        return from(aMember.getId());
    }
}

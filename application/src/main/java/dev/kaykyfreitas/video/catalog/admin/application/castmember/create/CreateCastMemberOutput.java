package dev.kaykyfreitas.video.catalog.admin.application.castmember.create;

import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMember;

public record CreateCastMemberOutput(
        String id
) {
    public static CreateCastMemberOutput from(final CastMember aMember) {
        return new CreateCastMemberOutput(aMember.getId().getValue());
    }
}

package dev.kaykyfreitas.video.catalog.admin.infrastructure.castmember.presenter;

import dev.kaykyfreitas.video.catalog.admin.application.castmember.retrieve.get.CastMemberOutput;
import dev.kaykyfreitas.video.catalog.admin.application.castmember.retrieve.list.CastMemberListOutput;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.castmember.models.CastMemberListResponse;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.castmember.models.CastMemberResponse;

public interface CastMemberPresenter {

    static CastMemberResponse present(final CastMemberOutput castMember) {
        return new CastMemberResponse(
                castMember.id(),
                castMember.name(),
                castMember.type().name(),
                castMember.createdAt().toString(),
                castMember.updatedAt().toString()
        );
    }

    static CastMemberListResponse present(final CastMemberListOutput castMember) {
        return new CastMemberListResponse(
                castMember.id(),
                castMember.name(),
                castMember.type().name(),
                castMember.createdAt().toString()
        );
    }

}

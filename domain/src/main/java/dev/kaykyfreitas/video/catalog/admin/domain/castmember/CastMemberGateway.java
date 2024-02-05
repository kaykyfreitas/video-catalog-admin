package dev.kaykyfreitas.video.catalog.admin.domain.castmember;

import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.SearchQuery;

import java.util.Optional;

public interface CastMemberGateway {
    CastMember create(CastMember aCastMember);
    void deleteById(CastMemberId anId);
    Optional<CastMember> findById(CastMemberId anId);
    CastMember update(CastMember aCastMember);
    Pagination<CastMember> findAll(SearchQuery aQuery);
}

package dev.kaykyfreitas.video.catalog.admin.application.castmember.retrieve.list;

import dev.kaykyfreitas.video.catalog.admin.application.UseCase;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.SearchQuery;

public sealed abstract class ListCastMembersUseCase
        extends UseCase<SearchQuery, Pagination<CastMemberListOutput>>
        permits DefaultListCastMembersUseCase {
}

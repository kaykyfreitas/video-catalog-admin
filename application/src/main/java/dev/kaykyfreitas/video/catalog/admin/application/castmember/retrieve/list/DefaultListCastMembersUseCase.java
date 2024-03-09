package dev.kaykyfreitas.video.catalog.admin.application.castmember.retrieve.list;

import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.SearchQuery;

import java.util.Objects;

public non-sealed class DefaultListCastMembersUseCase extends ListCastMembersUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultListCastMembersUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public Pagination<CastMemberListOutput> execute(final SearchQuery anQuery) {
        return this.castMemberGateway.findAll(anQuery)
                .map(CastMemberListOutput::from);
    }
}

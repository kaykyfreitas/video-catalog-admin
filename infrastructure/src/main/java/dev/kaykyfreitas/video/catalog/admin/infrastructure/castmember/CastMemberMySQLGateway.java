package dev.kaykyfreitas.video.catalog.admin.infrastructure.castmember;

import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMember;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberId;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.SearchQuery;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.castmember.persistence.CastMemberJpaEntity;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.castmember.persistence.CastMemberRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CastMemberMySQLGateway implements CastMemberGateway {

    private final CastMemberRepository castMemberRepository;

    public CastMemberMySQLGateway(final CastMemberRepository castMemberRepository) {
        this.castMemberRepository = Objects.requireNonNull(castMemberRepository);
    }

    @Override
    public CastMember create(final CastMember aCastMember) {
        return save(aCastMember);
    }

    @Override
    public void deleteById(final CastMemberId anId) {

    }

    @Override
    public Optional<CastMember> findById(final CastMemberId anId) {
        return Optional.empty();
    }

    @Override
    public CastMember update(final CastMember aCastMember) {
        return null;
    }

    @Override
    public Pagination<CastMember> findAll(final SearchQuery aQuery) {
        return null;
    }

    private CastMember save(CastMember aCastMember) {
        return this.castMemberRepository.save(CastMemberJpaEntity.from(aCastMember))
                .toAggregate();
    }

}

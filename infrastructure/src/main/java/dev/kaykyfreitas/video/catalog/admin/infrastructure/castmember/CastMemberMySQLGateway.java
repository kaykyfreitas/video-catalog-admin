package dev.kaykyfreitas.video.catalog.admin.infrastructure.castmember;

import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMember;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberId;
import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryId;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.SearchQuery;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.castmember.persistence.CastMemberJpaEntity;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.castmember.persistence.CastMemberRepository;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

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
    public void deleteById(final CastMemberId aMemberId) {
        final var anId = aMemberId.getValue();
        if (this.castMemberRepository.existsById(anId))
            this.castMemberRepository.deleteById(anId);
    }

    @Override
    public Optional<CastMember> findById(final CastMemberId anId) {
        return this.castMemberRepository.findById(anId.getValue())
                .map(CastMemberJpaEntity::toAggregate);
    }

    @Override
    public CastMember update(final CastMember aCastMember) {
        return save(aCastMember);
    }

    @Override
    public Pagination<CastMember> findAll(final SearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.Direction.fromString(aQuery.direction()),
                aQuery.sort()
        );

        final var whereClause = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult = this.castMemberRepository.findAll(whereClause, page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CastMemberJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public List<CastMemberId> existsByIds(final Iterable<CastMemberId> castMemberIds) {
        final var ids = StreamSupport.stream(castMemberIds.spliterator(), false)
                .map(CastMemberId::getValue)
                .toList();
        return this.castMemberRepository.existsByIds(ids).stream()
                .map(CastMemberId::from)
                .toList();
    }

    private CastMember save(final CastMember aCastMember) {
        return this.castMemberRepository.save(CastMemberJpaEntity.from(aCastMember))
                .toAggregate();
    }

    private Specification<CastMemberJpaEntity> assembleSpecification(final String terms) {
        return SpecificationUtils.like("name", terms);
    }

}

package dev.kaykyfreitas.video.catalog.admin.application.castmember.delete;

import dev.kaykyfreitas.video.catalog.admin.IntegrationTest;
import dev.kaykyfreitas.video.catalog.admin.domain.Fixture;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMember;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberId;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.castmember.persistence.CastMemberJpaEntity;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@IntegrationTest
public class DeleteCastMemberUseCaseIT {

    @Autowired
    private DefaultDeleteCastMemberUseCase useCase;

    @SpyBean
    private CastMemberGateway castMemberGateway;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @Test
    public void givenAValidId_whenCallsDeleteCastMember_shouldDeleteId() {
        // given
        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());
        final var aMemberTwo = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());

        final var expectedId = aMember.getId();

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));
        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMemberTwo));

        Assertions.assertEquals(2, castMemberRepository.count());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(any());

        Assertions.assertEquals(1, castMemberRepository.count());
        Assertions.assertFalse(castMemberRepository.existsById(expectedId.getValue()));
        Assertions.assertTrue(castMemberRepository.existsById(aMemberTwo.getId().getValue()));

    }

    @Test
    public void givenAnInvalidId_whenCallsDeleteCastMember_shouldBeOk() {
        // given
        this.castMemberRepository.saveAndFlush(
                CastMemberJpaEntity.from(
                        CastMember.newMember(Fixture.name(), Fixture.CastMembers.type())
                )
        );
        Assertions.assertEquals(1, castMemberRepository.count());

        final var expectedId = CastMemberId.from("123");

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(any());
        Assertions.assertEquals(1, castMemberRepository.count());
    }

    @Test
    public void givenAValidId_whenCallsDeleteCastMemberAndGatewayThrowsException_shouldReceiveException() {
        // given
        this.castMemberRepository.saveAndFlush(
                CastMemberJpaEntity.from(
                        CastMember.newMember(Fixture.name(), Fixture.CastMembers.type())
                )
        );
        Assertions.assertEquals(1, castMemberRepository.count());

        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());

        final var expectedId = aMember.getId();

        doThrow(new IllegalStateException("gateway error"))
                .when(castMemberGateway).deleteById(any());

        // when
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        // then
        verify(castMemberGateway).deleteById(any());
        Assertions.assertEquals(1, castMemberRepository.count());
    }

}

package dev.kaykyfreitas.video.catalog.admin.application.castmember.update;

import dev.kaykyfreitas.video.catalog.admin.domain.Identifier;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMember;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberGateway;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberId;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberType;
import dev.kaykyfreitas.video.catalog.admin.domain.exceptions.NotFoundException;
import dev.kaykyfreitas.video.catalog.admin.domain.exceptions.NotificationException;
import dev.kaykyfreitas.video.catalog.admin.domain.validation.handler.Notification;

import java.util.Objects;
import java.util.function.Supplier;

public non-sealed class DefaultUpdateCastMemberUseCase extends UpdateCastMemberUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultUpdateCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public UpdateCastMemberOutput execute(final UpdateCastMemberCommand aCommand) {
        final var anId = CastMemberId.from(aCommand.id());
        final var aName = aCommand.name();
        final var aType = aCommand.type();

        final var aMember = this.castMemberGateway.findById(anId)
                .orElseThrow(notFound(anId));

        final var notification = Notification.create();
        notification.validate(() -> aMember.update(aName, aType));

        if (notification.hasError()) notify(anId, notification);

        return UpdateCastMemberOutput.from(this.castMemberGateway.update(aMember));
    }

    private void notify(final Identifier anId, final Notification notification) {
        throw new NotificationException("could not update aggregate castmember %s".formatted(anId.getValue()), notification);
    }

    private Supplier<NotFoundException> notFound(final CastMemberId anId) {
        return () -> NotFoundException.with(CastMember.class, anId);
    }

}

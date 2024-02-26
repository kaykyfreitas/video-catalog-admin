package dev.kaykyfreitas.video.catalog.admin.application.castmember.update;

import dev.kaykyfreitas.video.catalog.admin.application.UseCase;

public sealed abstract class UpdateCastMemberUseCase
        extends UseCase<UpdateCastMemberCommand, UpdateCastMemberOutput>
        permits DefaultUpdateCastMemberUseCase {
}

package dev.kaykyfreitas.video.catalog.admin.application.castmember.delete;

import dev.kaykyfreitas.video.catalog.admin.application.UnitUseCase;

public sealed abstract class DeleteCastMemberUseCase
        extends UnitUseCase<String>
        permits DefaultDeleteCastMemberUseCase {
}

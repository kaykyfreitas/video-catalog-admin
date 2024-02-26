package dev.kaykyfreitas.video.catalog.admin.application.castmember.create;

import dev.kaykyfreitas.video.catalog.admin.application.UseCase;

public sealed abstract class CreateCastMemberUseCase
        extends UseCase<CreateCastMemberCommand, CreateCastMemberOutput>
        permits DefaultCreateCastMemberUseCase {
}

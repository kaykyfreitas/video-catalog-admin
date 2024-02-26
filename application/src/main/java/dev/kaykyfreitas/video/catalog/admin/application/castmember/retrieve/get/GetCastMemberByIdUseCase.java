package dev.kaykyfreitas.video.catalog.admin.application.castmember.retrieve.get;

import dev.kaykyfreitas.video.catalog.admin.application.UseCase;

public sealed abstract class GetCastMemberByIdUseCase
        extends UseCase<String, CastMemberOutput>
        permits DefaultGetCastMemberByIdUseCase {
}

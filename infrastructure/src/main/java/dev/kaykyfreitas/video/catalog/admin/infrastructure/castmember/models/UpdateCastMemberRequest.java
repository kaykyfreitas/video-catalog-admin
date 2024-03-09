package dev.kaykyfreitas.video.catalog.admin.infrastructure.castmember.models;

import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberType;

public record UpdateCastMemberRequest(String name, CastMemberType type) {
}

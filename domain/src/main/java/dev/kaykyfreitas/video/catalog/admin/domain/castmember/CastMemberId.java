package dev.kaykyfreitas.video.catalog.admin.domain.castmember;

import dev.kaykyfreitas.video.catalog.admin.domain.Identifier;
import dev.kaykyfreitas.video.catalog.admin.domain.utils.IdUtils;

import java.util.Objects;

public class CastMemberId extends Identifier {
    private final String value;

    private CastMemberId(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static CastMemberId unique() {
        return CastMemberId.from(IdUtils.uuid());
    }

    public static CastMemberId from(final String anId) {
        return new CastMemberId(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final CastMemberId that = (CastMemberId) object;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

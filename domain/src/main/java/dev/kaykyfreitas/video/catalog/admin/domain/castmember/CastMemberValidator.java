package dev.kaykyfreitas.video.catalog.admin.domain.castmember;

import dev.kaykyfreitas.video.catalog.admin.domain.validation.Error;
import dev.kaykyfreitas.video.catalog.admin.domain.validation.ValidationHandler;
import dev.kaykyfreitas.video.catalog.admin.domain.validation.Validator;

import java.util.Objects;

public class CastMemberValidator extends Validator {

    private static final Integer NAME_MAX_LENGTH = 255;
    private static final Integer NAME_MIN_LENGTH = 3;

    private final CastMember castMember;

    public CastMemberValidator(final CastMember aCastMember, final ValidationHandler aHandler) {
        super(aHandler);
        this.castMember = aCastMember;
    }

    @Override
    public void validate() {
        this.checkTypeConstraints();
        this.checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.castMember.getName();
        if (Objects.isNull(name)) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }

    private void checkTypeConstraints() {
        final var type = this.castMember.getType();

        if (type == null) {
            this.validationHandler().append(new Error("'type' should not be null"));
        }
    }
}

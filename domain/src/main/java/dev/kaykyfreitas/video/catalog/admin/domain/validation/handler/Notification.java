package dev.kaykyfreitas.video.catalog.admin.domain.validation.handler;

import dev.kaykyfreitas.video.catalog.admin.domain.exceptions.DomainException;
import dev.kaykyfreitas.video.catalog.admin.domain.validation.Error;
import dev.kaykyfreitas.video.catalog.admin.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;

    private Notification(List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Error anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }

    public static Notification create(final Throwable aThrowable) {
        return create(new Error(aThrowable.getMessage()));
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(ValidationHandler aHandler) {
        this.errors.addAll(aHandler.getErrors());
        return this;
    }

    @Override
    public ValidationHandler validate(Validation aValidation) {
        try {
            aValidation.validate();
        } catch (final DomainException e) {
            this.errors.addAll(e.getErrors());
        } catch (final Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}

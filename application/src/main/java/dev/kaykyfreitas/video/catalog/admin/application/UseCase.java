package dev.kaykyfreitas.video.catalog.admin.application;

import dev.kaykyfreitas.video.catalog.admin.domain.Category;

public class UseCase {
    public Category execute() {
        return new Category();
    }
}
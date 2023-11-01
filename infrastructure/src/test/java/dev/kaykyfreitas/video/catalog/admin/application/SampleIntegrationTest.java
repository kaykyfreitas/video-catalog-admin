package dev.kaykyfreitas.video.catalog.admin.application;

import dev.kaykyfreitas.video.catalog.admin.IntegrationTest;
import dev.kaykyfreitas.video.catalog.admin.application.category.create.CreateCategoryUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class SampleIntegrationTest {

    @Autowired
    private CreateCategoryUseCase useCase;

    @Test
    public void test() {
        Assertions.assertNotNull(useCase);
    }
}

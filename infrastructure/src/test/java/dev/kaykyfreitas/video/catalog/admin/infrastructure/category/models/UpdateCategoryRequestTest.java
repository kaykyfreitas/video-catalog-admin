package dev.kaykyfreitas.video.catalog.admin.infrastructure.category.models;

import dev.kaykyfreitas.video.catalog.admin.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

@JacksonTest
public class UpdateCategoryRequestTest {

    @Autowired
    private JacksonTester<UpdateCategoryRequest> json;

    @Test
    public void testUnmarshall() throws IOException {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var json = """
        {
          "name": "%s",
          "description": "%s",
          "is_active": %s
        }
        """.formatted(expectedName, expectedDescription, expectedIsActive);

        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("description", expectedDescription)
                .hasFieldOrPropertyWithValue("active", expectedIsActive);
    }
}

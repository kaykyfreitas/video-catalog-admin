package dev.kaykyfreitas.video.catalog.admin.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kaykyfreitas.video.catalog.admin.ControllerTest;
import dev.kaykyfreitas.video.catalog.admin.application.category.create.CreateCategoryOutput;
import dev.kaykyfreitas.video.catalog.admin.application.category.create.CreateCategoryUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.category.retrieve.get.CategoryOutput;
import dev.kaykyfreitas.video.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.category.retrieve.list.CategoryListOutput;
import dev.kaykyfreitas.video.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.category.update.UpdateCategoryOutput;
import dev.kaykyfreitas.video.catalog.admin.application.category.update.UpdateCategoryUseCase;
import dev.kaykyfreitas.video.catalog.admin.domain.category.Category;
import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryId;
import dev.kaykyfreitas.video.catalog.admin.domain.exceptions.DomainException;
import dev.kaykyfreitas.video.catalog.admin.domain.exceptions.NotFoundException;
import dev.kaykyfreitas.video.catalog.admin.domain.pagination.Pagination;
import dev.kaykyfreitas.video.catalog.admin.domain.validation.Error;
import dev.kaykyfreitas.video.catalog.admin.domain.validation.handler.Notification;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.category.models.CreateCategoryApiInput;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.category.models.UpdateCategoryApiInput;
import io.vavr.API;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @MockBean
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @MockBean
    private UpdateCategoryUseCase updateCategoryUseCase;

    @MockBean
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @MockBean
    private ListCategoriesUseCase listCategoriesUseCase;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_thenShouldReturnCategoryId() throws Exception {
        // given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var anInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(Right(CreateCategoryOutput.from("123")));

        // when
        final var request = MockMvcRequestBuilders
                .post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsBytes(anInput));

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // then
        response
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/categories/123"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo("123")));

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnNotification() throws Exception {
        // given
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedMessage = "'name' should not be null";

        final var anInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedMessage))));

        // then
        final var request = MockMvcRequestBuilders
                .post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsBytes(anInput));

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // then
        response
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenAInvalidCommand_whenCallsCreateCategory_thenShouldReturnDomainException() throws Exception {
        // given
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedMessage = "'name' should not be null";

        final var anInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error(expectedMessage)));

        // when
        final var request = MockMvcRequestBuilders
                .post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsBytes(anInput));

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // then
        response
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedMessage)))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenAValidId_whenCallsGetCategory_shouldCategory() throws Exception {
        // given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = aCategory.getId().getValue();

        when(getCategoryByIdUseCase.execute(any()))
                .thenReturn(CategoryOutput.from(aCategory));

        // when
        final var request = MockMvcRequestBuilders.get("/categories/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // then
        response
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)))
                .andExpect(jsonPath("$.name", equalTo(expectedName)))
                .andExpect(jsonPath("$.description", equalTo(expectedDescription)))
                .andExpect(jsonPath("$.is_active", equalTo(expectedIsActive)))
                .andExpect(jsonPath("$.created_at", equalTo(aCategory.getCreatedAt().toString())))
                .andExpect(jsonPath("$.updated_at", equalTo(aCategory.getUpdatedAt().toString())))
                .andExpect(jsonPath("$.deleted_at", equalTo(aCategory.getDeletedAt())));

        verify(getCategoryByIdUseCase, times(1)).execute(eq(expectedId));
    }

    @Test
    public void givenAInvalidId_whenCallsGetCategory_shouldReturnNotFound() throws Exception {
        // given
        final var expectedErrorMessage = "category with id 123 was not found";
        final var expectedId = CategoryId.from("123");

        // when
        when(getCategoryByIdUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Category.class, expectedId));

        final var request = MockMvcRequestBuilders.get("/categories/{id}", expectedId.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // then
        response
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_thenShouldReturnCategoryId() throws Exception {
        // given
        final var expectedId = "123";
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        when(updateCategoryUseCase.execute(any()))
                .thenReturn(Right(UpdateCategoryOutput.from(expectedId)));

        final var aCommand = new UpdateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        // when
        final var request = MockMvcRequestBuilders.put("/categories/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // then
        response
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)));

        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                && Objects.equals(expectedDescription, cmd.description())
                && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenACommandWithInvalidId_whenCallsUpdateCategory_thenShouldReturnNotFoundException() throws Exception {
        // given
        final var expectedId = "not-found";
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedErrorMessage = "category with id not-found was not found";

        when(updateCategoryUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Category.class, CategoryId.from(expectedId)));

        final var aCommand = new UpdateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        // when
        final var request = MockMvcRequestBuilders.put("/categories/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // then
        response
                .andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));

        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                && Objects.equals(expectedDescription, cmd.description())
                && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() throws Exception {
        // given
        final var expectedId = "123";
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        when(updateCategoryUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedErrorMessage))));

        final var aCommand = new UpdateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        // when
        final var request = MockMvcRequestBuilders.put("/categories/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // then
        response
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(expectedErrorCount)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                && Objects.equals(expectedDescription, cmd.description())
                && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldReturnNoContent() throws Exception {
        // given
        final var expectedId = "123";

        doNothing().when(deleteCategoryUseCase).execute(any());

        // when
        final var request = MockMvcRequestBuilders.delete("/categories/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.log());

        // then
        response
                .andExpect(status().isNoContent());

        verify(deleteCategoryUseCase, times(1)).execute(eq(expectedId));
    }

    @Test
    public void givenValidParams_whenCallsListCategories_shouldReturnCategories() throws Exception {
        // given
        final var aCategory = Category.newCategory("Movies", null, true);

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "movies";
        final var expectedSort = "description";
        final var expectedDirection = "desc";
        final var expectedItemsCount = 1;
        final var expectedTotal = 1;

        final var expectedItems = List.of(CategoryListOutput.from(aCategory));

        when(listCategoriesUseCase.execute(any()))
                .thenReturn(new Pagination<>(expectedPage, expectedPerPage, expectedTotal, expectedItems));

        // when
        final var request = MockMvcRequestBuilders.get("/categories")
                .queryParam("page", String.valueOf(expectedPage))
                .queryParam("perPage", String.valueOf(expectedPerPage))
                .queryParam("sort", expectedSort)
                .queryParam("dir", expectedDirection)
                .queryParam("search", expectedTerms)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.log());

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", equalTo(expectedPage)))
                .andExpect(jsonPath("$.per_page", equalTo(expectedPerPage)))
                .andExpect(jsonPath("$.total", equalTo(expectedTotal)))
                .andExpect(jsonPath("$.items", hasSize(expectedItemsCount)))
                .andExpect(jsonPath("$.items[0].id", equalTo(aCategory.getId().getValue())))
                .andExpect(jsonPath("$.items[0].name", equalTo(aCategory.getName())))
                .andExpect(jsonPath("$.items[0].description", equalTo(aCategory.getDescription())))
                .andExpect(jsonPath("$.items[0].is_active", equalTo(aCategory.isActive())))
                .andExpect(jsonPath("$.items[0].created_at", equalTo(aCategory.getCreatedAt().toString())))
                .andExpect(jsonPath("$.items[0].deleted_at", equalTo(aCategory.getDeletedAt())));

        verify(listCategoriesUseCase, times(1)).execute(argThat(query ->
                Objects.equals(expectedPage, query.page())
                && Objects.equals(expectedPerPage, query.perPage())
                && Objects.equals(expectedDirection, query.direction())
                && Objects.equals(expectedSort, query.sort())
                && Objects.equals(expectedTerms, query.terms())
        ));
    }
}

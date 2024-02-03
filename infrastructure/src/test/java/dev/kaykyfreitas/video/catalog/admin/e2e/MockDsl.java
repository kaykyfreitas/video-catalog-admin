package dev.kaykyfreitas.video.catalog.admin.e2e;

import dev.kaykyfreitas.video.catalog.admin.domain.Identifier;
import dev.kaykyfreitas.video.catalog.admin.domain.category.CategoryId;
import dev.kaykyfreitas.video.catalog.admin.domain.genre.GenreId;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.category.models.CategoryResponse;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.category.models.CreateCategoryRequest;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.category.models.UpdateCategoryRequest;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.configuration.json.Json;
import dev.kaykyfreitas.video.catalog.admin.infrastructure.genre.models.CreateGenreRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.function.Function;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface MockDsl {

    MockMvc mvc();

    /**
     * Category
     */
    default CategoryId givenACategory(final String aName, final String aDescription, final boolean isActive) throws Exception {
        final var aRequestBody = new CreateCategoryRequest(aName, aDescription, isActive);
        final var actualId = this.given("/categories", aRequestBody);
        return CategoryId.from(actualId);
    }

    default ResultActions listCategories(final int page, final int perPage, final String search) throws Exception {
        return this.listCategories(page, perPage, search, "", "");
    }

    default ResultActions listCategories(final int page, final int perPage) throws Exception {
        return this.listCategories(page, perPage, "", "", "");
    }

    default ResultActions listCategories(
            final int page,
            final int perPage,
            final String search,
            final String sort,
            final String direction
    ) throws Exception {
        return this.list("/categories", page, perPage, search, sort, direction);
    }

    default ResultActions deleteACategory(final Identifier anId) throws Exception {
        return this.delete("/categories/", anId);
    }

    default ResultActions updateACategory(final Identifier anId, final UpdateCategoryRequest aRequest) throws Exception {
        return this.update("/categories/", anId, aRequest);
    }

    default CategoryResponse retrieveACategory(final Identifier anId) throws Exception {
        return this.retrieve("/categories/", anId, CategoryResponse.class);
    }

    default <A, D> List<D> mapTo(final List<A> actual, final Function<A, D> mapper) {
        return actual.stream()
                .map(mapper)
                .toList();
    }

    private String given(final String url, final Object body) throws Exception {
        final var aRequest = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(body));

        return this.mvc().perform(aRequest)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location")
                .replace("%s/".formatted(url), "");
    }

    private ResultActions list(
            final String url,
            final int page,
            final int perPage,
            final String search,
            final String sort,
            final String direction
    ) throws Exception {
        final var aRequest = MockMvcRequestBuilders.get(url)
                .queryParam("page", String.valueOf(page))
                .queryParam("perPage", String.valueOf(perPage))
                .queryParam("search", search)
                .queryParam("sort", sort)
                .queryParam("dir", direction)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        return this.mvc().perform(aRequest);
    }

    private <T> T retrieve(final String url, final Identifier anId, final Class<T> clazz) throws Exception {
        final var aRequest = MockMvcRequestBuilders.get(url + anId.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        final var json = this.mvc().perform(aRequest)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        return Json.readValue(json, clazz);
    }

    private ResultActions delete(final String url, final Identifier anId) throws Exception {
        final var aRequest = MockMvcRequestBuilders.delete(url + anId.getValue())
                .contentType(MediaType.APPLICATION_JSON);

        return this.mvc().perform(aRequest);
    }

    private ResultActions update(final String url, final Identifier anId, final Object aRequestBody) throws Exception {
        final var aRequest = MockMvcRequestBuilders.put(url + anId.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(aRequestBody));

        return this.mvc().perform(aRequest);
    }

}

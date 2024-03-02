package dev.kaykyfreitas.video.catalog.admin.infrastructure.configuration.usecases;

import dev.kaykyfreitas.video.catalog.admin.application.castmember.create.CreateCastMemberUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.castmember.create.DefaultCreateCastMemberUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.castmember.delete.DefaultDeleteCastMemberUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.castmember.delete.DeleteCastMemberUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.castmember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.castmember.retrieve.list.DefaultListCastMembersUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.castmember.retrieve.list.ListCastMembersUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.castmember.update.DefaultUpdateCastMemberUseCase;
import dev.kaykyfreitas.video.catalog.admin.application.castmember.update.UpdateCastMemberUseCase;
import dev.kaykyfreitas.video.catalog.admin.domain.castmember.CastMemberGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CatsMemberUseCaseConfig {

    private final CastMemberGateway castMemberGateway;

    public CatsMemberUseCaseConfig(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Bean
    public CreateCastMemberUseCase createCastMemberUseCase() {
        return new DefaultCreateCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public DeleteCastMemberUseCase deleteCastMemberUseCase() {
        return new DefaultDeleteCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public GetCastMemberByIdUseCase getCastMemberByIdUseCase() {
        return new DefaultGetCastMemberByIdUseCase(castMemberGateway);
    }

    @Bean
    public ListCastMembersUseCase listCastMembersUseCase() {
        return new DefaultListCastMembersUseCase(castMemberGateway);
    }

    @Bean
    public UpdateCastMemberUseCase updateCastMemberUseCase() {
        return new DefaultUpdateCastMemberUseCase(castMemberGateway);
    }

}

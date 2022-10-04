package com.algaworks.algafood.api.v1.openapi;

import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.domain.model.Cidade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Cidades", description = "Gerencia as cidades")
public interface CidadeControllerOpenApi {

    @Operation(summary = "Busca uma cidade por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Cidade encontrada"),
            @ApiResponse(responseCode = "400", description = "ID da cidade inválido"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada")
    })

    CidadeModel buscar(@Parameter(description = "ID de uma cidade", example = "1", required = true)
                       Long id);

    @Operation(summary = "Lista as cidades")
    @GetMapping
    CollectionModel<CidadeModel> listar();

    @Operation(summary = "Cadastra uma cidade",
        description = "Cadastro de uma cidade necessita de um estado e um nome válido")
    @PostMapping
    CidadeModel adicionar(@RequestBody Cidade cidade);

    @Operation(summary = "Remove uma cidade por ID")
    void remover(@Parameter(description = "ID de uma cidade", example = "1", required = true)
                 Long id);

    @Operation(summary = "Atualiza uma cidade por ID")
    Cidade atualizar(
        @Parameter(description = "ID de uma cidade", example = "1", required = true) Long id,
        @RequestBody(description = "Representação de uma nova cidade") Cidade cidade);
}

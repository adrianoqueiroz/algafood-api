package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements com.algaworks.algafood.api.v1.openapi.RestauranteProdutoControllerOpenApi {

    private final ProdutoRepository produtoRepository;
    private final ProdutoService cadastroProduto;
    private final RestauranteService restauranteService;

    private static final ModelMapper modelMapper = new ModelMapper();


    @Override
    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId,
                                     @RequestParam(required = false) boolean incluirInativos) {

        Restaurante restaurante = restauranteService.buscar(restauranteId);

        List<Produto> todosProdutos = incluirInativos
            ? produtoRepository.findAtivosByRestaurante(restaurante)
            : produtoRepository.findAllByRestaurante(restaurante);

        return toCollectionModel(todosProdutos);
    }

    @Override
    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        return toModel(produto);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        Produto produto = toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = cadastroProduto.salvar(produto);

        return toModel(produto);
    }

    @Override
    @PutMapping("/{produtoId}")
    public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        copyToDomainObject(produtoInput, produtoAtual);

        produtoAtual = cadastroProduto.salvar(produtoAtual);

        return toModel(produtoAtual);
    }

    @Override
    public ProdutoModel toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoModel.class);
    }

    @Override
    public List<ProdutoModel> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
            .map(this::toModel)
            .collect(Collectors.toList());
    }
    @Override
    public Produto toDomainObject(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }

    @Override
    public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }
}

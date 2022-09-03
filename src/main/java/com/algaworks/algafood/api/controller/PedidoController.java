package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    private static final ModelMapper modelMapper = new ModelMapper();

    @GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
        List<Pedido> todosPedidos = pedidoRepository.findAll();

        List<PedidoResumoModel> pedidosResumoModel = resumoToCollectionModel(todosPedidos);

        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosResumoModel);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();

        if (isNotBlank(campos)) {
            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(
                campos.split(",")));
        } else {
            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
        }

        pedidosWrapper.setFilters(filterProvider);
        return pedidosWrapper;
    }

//    @GetMapping
//    public List<PedidoResumoModel> listar() {
//        List<Pedido> todosPedidos = pedidoRepository.findAll();
//
//        return resumoToCollectionModel(todosPedidos);
//    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable UUID codigoPedido) {
        Pedido pedido = emissaoPedido.buscar(codigoPedido);

        return toModel(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedido.emitir(novoPedido);

            return toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    public PedidoModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoModel.class);
    }

    public List<PedidoModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
            .map(this::toModel)
            .collect(Collectors.toList());
    }

    public PedidoResumoModel resumoToModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoModel.class);
    }

    public List<PedidoResumoModel> resumoToCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
            .map(this::resumoToModel)
            .collect(Collectors.toList());
    }

    public Pedido toDomainObject(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }
}

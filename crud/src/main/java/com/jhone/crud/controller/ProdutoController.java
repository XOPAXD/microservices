package com.jhone.crud.controller;

import com.jhone.crud.data.vo.ProdutoVO;
import com.jhone.crud.services.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import  static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import  static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoservice;
    private final PagedResourcesAssembler<ProdutoVO> assembler;

    @GetMapping(value = "/{id}", produces = {"application/json","application/xml","application/x-yaml"})
    public ProdutoVO findById(@PathVariable("id") Long id){
        ProdutoVO produtoVO = produtoservice.findById(id);
        produtoVO.add(linkTo(methodOn(ProdutoController.class).findById(id)).withSelfRel());
        return  produtoVO;
    }

    @GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> findAll(@RequestParam(value = "page",defaultValue = "0") int page,
                                     @RequestParam(value = "limit",defaultValue = "10") int limit,
                                     @RequestParam(value = "direction",defaultValue = "asc") String direction){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page,limit,Sort.by(sortDirection,"nome"));

        Page<ProdutoVO> produtos = produtoservice.findAll(pageable);
        produtos.stream()
                .forEach(p -> p.add(linkTo(methodOn(ProdutoController.class).findById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<ProdutoVO>> pagedmodel = assembler.toModel(produtos);
        return new ResponseEntity<>(pagedmodel, HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json","application/xml","application/x-yaml"},consumes = {"application/json","application/xml","application/x-yaml"})
    public ProdutoVO create(@RequestBody ProdutoVO produtoVO){
        ProdutoVO provo = produtoservice.create(produtoVO);
        provo.add(linkTo(methodOn(ProdutoController.class).findById(provo.getId())).withSelfRel());
        return provo;
    }

    @PutMapping(produces = {"application/json","application/xml","application/x-yaml"},consumes = {"application/json","application/xml","application/x-yaml"})
    public ProdutoVO update(@RequestBody ProdutoVO produtoVO){
        ProdutoVO provo = produtoservice.update(produtoVO);
        provo.add(linkTo(methodOn(ProdutoController.class).findById(provo.getId())).withSelfRel());
        return provo;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        produtoservice.delete(id);

        return ResponseEntity.ok().build();
    }
}

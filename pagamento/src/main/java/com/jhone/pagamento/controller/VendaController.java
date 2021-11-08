package com.jhone.pagamento.controller;

import com.jhone.pagamento.data.vo.VendaVO;
import com.jhone.pagamento.services.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("venda")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VendaController {

    private final VendaService vendaservice;
    private final PagedResourcesAssembler<VendaVO> assembler;

    @GetMapping(value = "/{id}", produces = {"application/json","application/xml","application/x-yaml"})
    public VendaVO findById(@PathVariable("id") Long id){
        VendaVO vendaVO = vendaservice.findById(id);
        vendaVO.add(linkTo(methodOn(VendaController.class).findById(id)).withSelfRel());
        return  vendaVO;
    }

    @GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> findAll(@RequestParam(value = "page",defaultValue = "0") int page,
                                     @RequestParam(value = "limit",defaultValue = "10") int limit,
                                     @RequestParam(value = "direction",defaultValue = "asc") String direction){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page,limit,Sort.by(sortDirection,"data"));

        Page<VendaVO> vendas = vendaservice.findAll(pageable);
        vendas.stream()
                .forEach(p -> p.add(linkTo(methodOn(VendaController.class).findById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<VendaVO>> pagedmodel = assembler.toModel(vendas);
        return new ResponseEntity<>(pagedmodel, HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json","application/xml","application/x-yaml"},consumes = {"application/json","application/xml","application/x-yaml"})
    public VendaVO create(@RequestBody VendaVO vendaVO){
        VendaVO provo = vendaservice.create(vendaVO);
        provo.add(linkTo(methodOn(VendaController.class).findById(provo.getId())).withSelfRel());
        return provo;
    }
}

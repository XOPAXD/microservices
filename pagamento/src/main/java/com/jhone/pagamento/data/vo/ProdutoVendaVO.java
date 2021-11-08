package com.jhone.pagamento.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.jhone.pagamento.entity.ProdutoVenda;

import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"id","idProduto","quantidade"})
public class ProdutoVendaVO extends RepresentationModel<ProdutoVendaVO> implements Serializable {

    private static  final long serialVersionUID = 1l;

    @JsonProperty("id")
    private Long id;
    @JsonProperty("idProduto")
    private Long idProduto;
    @JsonProperty("quantidade")
    private Integer quantidade;

    public static ProdutoVendaVO create(ProdutoVenda produtovenda){

        return new ModelMapper().map(produtovenda,ProdutoVendaVO.class);
    }
}

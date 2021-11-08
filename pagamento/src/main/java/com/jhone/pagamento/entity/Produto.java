package com.jhone.pagamento.entity;

import com.jhone.pagamento.data.vo.ProdutoVO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="produto")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Produto implements Serializable {

    private static  final long serialVersionUID = 1l;

    @Id
    private Long id;

    @Column(nullable = false)
    private Integer estoque;

    public static Produto create(ProdutoVO produtoVO){
        return new ModelMapper().map(produtoVO,Produto.class);
    }
}

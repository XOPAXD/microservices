package com.jhone.pagamento.entity;

import com.jhone.pagamento.data.vo.ProdutoVendaVO;
import com.jhone.pagamento.data.vo.VendaVO;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="venda")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Venda implements Serializable {
    private static  final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name="data",nullable = false)
    private Date data;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "venda",cascade = {CascadeType.REFRESH})
    private List<ProdutoVenda> produtos;

    @Column(name="valorTotal",nullable = false,length = 10)
    private Double valorTotal;

    public static Venda create(VendaVO vendaVO){
        return new ModelMapper().map(vendaVO,Venda.class);
    }
}

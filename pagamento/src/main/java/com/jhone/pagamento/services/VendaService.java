package com.jhone.pagamento.services;

import com.jhone.pagamento.data.vo.VendaVO;
import com.jhone.pagamento.entity.ProdutoVenda;
import com.jhone.pagamento.entity.Venda;
import com.jhone.pagamento.exception.ResourceNotFoundException;
import com.jhone.pagamento.repository.ProdutoVendaRepository;
import com.jhone.pagamento.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoVendaRepository produtoVendaRepository;

    public VendaVO create(VendaVO vendaVO){
        Venda venda = vendaRepository.save(Venda.create(vendaVO));
        List<ProdutoVenda> produtos = new ArrayList<>();

        vendaVO.getProdutos().forEach(p -> {
            ProdutoVenda pv = ProdutoVenda.create(p);
            pv.setVenda(venda);
            produtos.add(produtoVendaRepository.save(pv));
        });
        venda.setProdutos(produtos);
        return VendaVO.create(venda);
    }

    public Page<VendaVO> findAll(Pageable pageable){
        var page = vendaRepository.findAll(pageable);
        return page.map(this::convertToVendaVO);
    }

    public VendaVO findById(Long id){
        var entity = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado,para o id.: "+id));
        return VendaVO.create(entity);
    }

    private VendaVO convertToVendaVO(Venda venda){
        return VendaVO.create(venda);
    }
}

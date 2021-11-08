package com.jhone.crud.services;

import com.jhone.crud.data.vo.ProdutoVO;
import com.jhone.crud.entity.Produto;
import com.jhone.crud.exception.ResourceNotFoundException;
import com.jhone.crud.message.ProdutoSendMessage;
import com.jhone.crud.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoService {


    private final ProdutoRepository produtorepository;
    private final ProdutoSendMessage produtosendmessage;

    public ProdutoVO create(ProdutoVO produtoVO){
       ProdutoVO retornoVO = ProdutoVO.create(produtorepository.save(Produto.create(produtoVO)));
        produtosendmessage.SendMessage(retornoVO);
        return retornoVO;
    }

    public Page<ProdutoVO> findAll(Pageable pageable){
        var page = produtorepository.findAll(pageable);
        return page.map(this::convertToProdutoVO);
    }

    private ProdutoVO convertToProdutoVO(Produto produto){
        return ProdutoVO.create(produto);
    }

    public ProdutoVO findById(Long id){
        var entity = produtorepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado,para o id.: "+id));
        return ProdutoVO.create(entity);
    }

    public ProdutoVO update(ProdutoVO produtoVO){
        final Optional<Produto> optionalProduto = produtorepository.findById(produtoVO.getId());
        if(!optionalProduto.isPresent()){
            new ResourceNotFoundException("Objeto não encontrado,para o id");
        }
        return produtoVO.create(produtorepository.save(Produto.create(produtoVO)));
    }

    public void delete(Long id){
        var entity = produtorepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado,para o id.: "+id));

        produtorepository.delete(entity);
    }
}

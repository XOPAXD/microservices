package com.jhone.pagamento.config;

import com.jhone.pagamento.data.vo.ProdutoVO;
import com.jhone.pagamento.entity.Produto;
import com.jhone.pagamento.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoReceiveMessage {

    private final ProdutoRepository produtorepository;

    @RabbitListener(queues = {"${crud.rabbitmq.queue}"})
    public void receive(@Payload ProdutoVO produtoVO){
        produtorepository.save(Produto.create(produtoVO));
    }
}

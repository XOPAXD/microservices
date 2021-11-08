package com.jhone.crud.message;

import com.jhone.crud.data.vo.ProdutoVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProdutoSendMessage {

    @Value("${crud.rabbitmq.exchange}")
    String exchange;

    @Value("${crud.rabbitmq.routingkey}")
    String routingkey;

    public final RabbitTemplate rabbittemplate;

    @Autowired
    public ProdutoSendMessage(RabbitTemplate rabbittemplate) {
        this.rabbittemplate = rabbittemplate;
    }

    public void SendMessage(ProdutoVO produtovo){
        rabbittemplate.convertAndSend(exchange,routingkey,produtovo);
    }
}

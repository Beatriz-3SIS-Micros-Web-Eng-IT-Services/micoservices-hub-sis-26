package com.github.BeatrizRosa05.ms.pagamentos.tests;

import com.github.BeatrizRosa05.ms.pagamentos.entities.Pagamento;
import com.github.BeatrizRosa05.ms.pagamentos.entities.Status;

import java.math.BigDecimal;

//Classe para instanciar objetos
public class Factory {

    public static Pagamento createPagamento(){
        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);
        pagamento.setValor(BigDecimal.valueOf(32.25));
        pagamento.setNome("Brienne de Tarth");
        pagamento.setNumeroCartao("4718293744458291");
        pagamento.setValidade("07/15");
        pagamento.setCodigoSeguranca("354");
        pagamento.setStatus(Status.CRIADO);
        pagamento.setPedidoId(1L);
        return pagamento;
    }

    public static Pagamento createPagamentoSemId(){
        Pagamento pagamento = createPagamento();
        pagamento.setId(null);
        return pagamento;
    }
}

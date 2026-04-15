package com.github.BeatrizRosa05.ms.pagamentos.tests;

import com.github.BeatrizRosa05.ms.pagamentos.entities.Pagamento;
import com.github.BeatrizRosa05.ms.pagamentos.entities.Status;

import java.math.BigDecimal;

//Classe para instanciar objetos
public class Factory {

    public static Pagamento createPagamento(){
        Pagamento pagamento = new Pagamento(1L, BigDecimal.valueOf(32.25),
                "Brienne de Tarth", "4718293744458291", "07/15",
                "354", Status.CRIADO, 1L);
        return pagamento;
    }

    public static Pagamento createPagamentoSemId(){
        Pagamento pagamento = createPagamento();
        pagamento.setId(null);
        return pagamento;
    }
}

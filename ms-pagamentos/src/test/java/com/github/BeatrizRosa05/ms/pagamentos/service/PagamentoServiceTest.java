package com.github.BeatrizRosa05.ms.pagamentos.service;

import com.github.BeatrizRosa05.ms.pagamentos.exceptions.ResourceNotFoundException;
import com.github.BeatrizRosa05.ms.pagamentos.repository.PagamentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) //Nao sobe o contexto do Spring
public class PagamentoServiceTest {

    //Cenário: A SUT (classe testada) depende do PagamentoRepository

    @Mock //Cria um dublê (mock) do repository ara isolar o teste
    private PagamentoRepository pagamentoRepository;

    @InjectMocks //Cria a instancia real do service (SUT) e injeta os mocks nela
    private PagamentoService pagamentoService;

    //Nao acessa DB | Preparando os dados - variáveis
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp(){
        existingId = 1L;
        nonExistingId = Long.MAX_VALUE;
    }

    @Test
    void deletePagamentByIdShouldDeleteWhenIdExists(){
        //Arrange - prepara o comportamento do mock (stubbing)
        Mockito.when(pagamentoRepository.existsById(existingId)).thenReturn(true);

        pagamentoService.deletePagamentoById(existingId);

        //Verify(...) = verifica se o mock foi chamado
        //Verifica que o mock pagamentoRepository recebeu uma chamada ao metodo existsById
        Mockito.verify(pagamentoRepository).existsById(existingId);
        //Vaerifica se o metodo deleteById do repository foi chamado exatamente 1 vez (padrao)
        Mockito.verify(pagamentoRepository, Mockito.times(1)).deleteById(existingId);

    }

    @Test
    @DisplayName("deletePagamentoByID deveria lançar ResourceNotFoundException quando Id nao existir")
    void deletePagamentoByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){

        //Arange
        Mockito.when(pagamentoRepository.existsById(nonExistingId)).thenReturn(false);
        //Act _ Assert
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> {
                        pagamentoService.deletePagamentoById(nonExistingId);
        });

        //Verificações (behavior)
        Mockito.verify(pagamentoRepository).existsById(nonExistingId);
        //never() = equivalente a times (0) -> esse metodo nao pode ter sido chamado nenhuma vez.
        //anyLong() é um matcher (coringa): aceita qualquer valor long/Long
        Mockito.verify(pagamentoRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }

}/////////////////////////////


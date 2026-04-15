package com.github.BeatrizRosa05.ms.pagamentos.service;

import com.github.BeatrizRosa05.ms.pagamentos.dto.PagamentoDTO;
import com.github.BeatrizRosa05.ms.pagamentos.entities.Pagamento;
import com.github.BeatrizRosa05.ms.pagamentos.exceptions.ResourceNotFoundException;
import com.github.BeatrizRosa05.ms.pagamentos.repository.PagamentoRepository;
import com.github.BeatrizRosa05.ms.pagamentos.tests.Factory;
import org.hibernate.action.internal.EntityActionVetoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

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

    private Pagamento pagamento;

    @BeforeEach
    void setUp(){
        existingId = 1L;
        nonExistingId = Long.MAX_VALUE;

        pagamento = Factory.createPagamento();
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

    @Test
    void findPagamentoByIdShouldReturnPagamentoDTOWhenIdExists(){

        //Arange
        Mockito.when(pagamentoRepository.findById(existingId))
                .thenReturn(Optional.of(pagamento));

        //Act
        PagamentoDTO result = pagamentoService.findPagamentoById(existingId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(pagamento.getId(), result.getId());
        Assertions.assertEquals(pagamento.getValor(), result.getValor());

        Mockito.verify(pagamentoRepository).findById(existingId);
        Mockito.verifyNoMoreInteractions(pagamentoRepository);

    }

    @Test
    @DisplayName("Dado parâmetros válidos e Id nulo" +
    "quando chamar Salvar pagamento "+
    "então deve gerar Id e persistir um Pagamento")
    void givenValidParamsAndIdIsNull_whenSave_thenShouldPersistPagamento(){
        //Arange
        Mockito.when(pagamentoRepository.save(any(Pagamento.class)))
                .thenReturn(pagamento);
        pagamento.setId(null);
        PagamentoDTO inputDto = new PagamentoDTO(pagamento);
        //Act
        PagamentoDTO result = pagamentoService.savePagamento(inputDto);
        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(pagamento.getId(), result.getId());
        //Verify
        Mockito.verify(pagamentoRepository).save(any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }

    @Test
    void updatePagamentoShouldReturnPagamentoDTOWhenIdExists(){
        //Arange
        Long id = pagamento.getId();
        Mockito.when(pagamentoRepository.getReferenceById(id)).thenReturn(pagamento);
        Mockito.when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);
        //Act
        PagamentoDTO result = pagamentoService.updatePagamento(id, new PagamentoDTO(pagamento));
        //Assert e Verify
        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals(pagamento.getValor(), result.getValor());
        Mockito.verify(pagamentoRepository).getReferenceById(id);
        Mockito.verify(pagamentoRepository).save(Mockito.any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }

    @Test
    void updatePagamentoShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){

        Mockito.when(pagamentoRepository.getReferenceById(nonExistingId))
                .thenThrow(EntityActionVetoException.class);
        PagamentoDTO inputDto = new PagamentoDTO(pagamento);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> pagamentoService.updatePagamento(nonExistingId, inputDto));

        Mockito.verify(pagamentoRepository).getReferenceById(nonExistingId);
        Mockito.verify(pagamentoRepository, Mockito.never()).save(Mockito.any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(pagamentoRepository);


    }


}/////////////////////////////


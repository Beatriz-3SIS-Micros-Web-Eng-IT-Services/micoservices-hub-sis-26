package com.github.BeatrizRosa05.ms.pagamentos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.BeatrizRosa05.ms.pagamentos.dto.PagamentoDTO;
import com.github.BeatrizRosa05.ms.pagamentos.entities.Pagamento;
import com.github.BeatrizRosa05.ms.pagamentos.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.print.attribute.standard.Media;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional //Rollback DB
public class PagamentoControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Pagamento pagamento;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void SetUp(){
        existingId =1L;
        nonExistingId = Long.MAX_VALUE;
    }
    @Test
    void findAllPagamentosShouldReturn200AndJsonArray() throws Exception{

        mockMvc.perform(get("/pagamentos")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[*].id").isArray())
                .andExpect(jsonPath("$[1].valor").value(3599.0));

    }///

    @Test
    void findPagamentoByIdShouldReturn200WhenIdExists() throws Exception{

        mockMvc.perform(get("/pagamentos/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("John Snow"))
                .andExpect(jsonPath("$.status").value("CRIADO"));
    }///

    @Test
    void findPagamentoByIdShouldReturn404WhenIdDoesNotExist() throws Exception{

        mockMvc.perform(get("/pagamentos/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }///

    @Test
    void createPagamentoShouldReturn201WhenValid() throws Exception{

        PagamentoDTO requestDTO = new PagamentoDTO(Factory.createPagamentoSemId());
        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(post("/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value("CRIADO"));
    }///

    @Test
    void createPagamentoShouldReturn422WhenInvalid() throws Exception{

        Pagamento pagamentoInvalido = Factory.createPagamentoSemId();
        pagamentoInvalido.setValor(BigDecimal.valueOf(0));
        pagamentoInvalido.setNome(null);
        PagamentoDTO requestDTO = new PagamentoDTO(pagamentoInvalido);
        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(post("/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Dados inválidos"))
                .andExpect(jsonPath("$.errors").isArray());
    }///

    @Test
    void updatePagamentoShouldReturn200WhenIdExists() throws Exception{

        pagamento = Factory.createPagamento();
        PagamentoDTO requestDTO = new PagamentoDTO(pagamento);
        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(put("/pagamentos/{id}",existingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.nome").value(pagamento.getNome()));
    }///

    @Test
    void updatePagamentoShouldReturn422WhenInvalid()throws Exception{
        pagamento = Factory.createPagamento();
        pagamento.setNome(null);
        pagamento.setValor(BigDecimal.valueOf(-32.05));
        pagamento.setPedidoId(null);

        PagamentoDTO requestDTO = new PagamentoDTO(pagamento);
        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(put("/pagamentos/{id}", existingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Dados inválidos"))
                .andExpect(jsonPath("$.errors").isArray());
    }///

    @Test
    void updatePagamentoShouldReturn404WhenIdDoesNotExist() throws Exception{

        pagamento = Factory.createPagamento();
        PagamentoDTO requestDTO = new PagamentoDTO(pagamento);

        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(put("/pagamentos/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isNotFound());
    }///

    @Test
    void deletePagamentoShouldReturn204WhenIdExists() throws Exception{

        mockMvc.perform(delete("/pagamentos/{id}", existingId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }///

    @Test
    void deletePagamentoShouldReturn404WhenIdDoesNotExist() throws Exception{

        mockMvc.perform(delete("/pagamentos/{id}", nonExistingId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }///






}/////////////////////////

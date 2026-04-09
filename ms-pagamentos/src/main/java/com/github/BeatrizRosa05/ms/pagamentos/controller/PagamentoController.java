package com.github.BeatrizRosa05.ms.pagamentos.controller;

import com.github.BeatrizRosa05.ms.pagamentos.dto.PagamentoDTO;
import com.github.BeatrizRosa05.ms.pagamentos.entities.Pagamento;
import com.github.BeatrizRosa05.ms.pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> getAll(){

        List<PagamentoDTO> pagamentoDTOS = pagamentoService.findAllPagamentos();
        return ResponseEntity.ok(pagamentoDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> getById (@PathVariable Long id){

        PagamentoDTO pagamentoDTO = pagamentoService.findPagamentoById(id);
        return ResponseEntity.ok(pagamentoDTO);
    }

    @PostMapping
    private ResponseEntity<PagamentoDTO> createPagamento(@RequestBody @Valid PagamentoDTO pagamentoDTO){

        pagamentoDTO = pagamentoService.savePagamento(pagamentoDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(pagamentoDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(pagamentoDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> updateProduto(@PathVariable Long id, @Valid @RequestBody PagamentoDTO pagamentoDTO){

        pagamentoDTO = pagamentoService.updatePagamento(id, pagamentoDTO);

        return ResponseEntity.ok(pagamentoDTO);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id){

        pagamentoService.deletePagamentoById(id);;

        return ResponseEntity.noContent().build();

    }

}//////////

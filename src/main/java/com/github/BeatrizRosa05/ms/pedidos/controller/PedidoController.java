package com.github.BeatrizRosa05.ms.pedidos.controller;

import com.github.BeatrizRosa05.ms.pedidos.dto.PedidoDTO;
import com.github.BeatrizRosa05.ms.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedidos(){
        List<PedidoDTO> list = pedidoService.findAllPedidos();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedido(@PathVariable Long id){

        PedidoDTO pedidoDTO = pedidoService.findPedidoById(id);
        return ResponseEntity.ok(pedidoDTO);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody @Valid PedidoDTO pedidoDTO){

    pedidoDTO = pedidoService.savePedido(pedidoDTO);

    URI uri = ServletUriComponentsBuilder
            .fromCurrentRequestUri()
            .path("/{id}")
            .buildAndExpand(pedidoDTO.getId())
            .toUri();

    return ResponseEntity.created(uri).body(pedidoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable Long id, @Valid @RequestBody PedidoDTO pedidoDTO){

        pedidoDTO = pedidoService.updatePedido(id, pedidoDTO);
        return  ResponseEntity.ok(pedidoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id){

        pedidoService.deletePedidoById(id);

        return ResponseEntity.noContent().build();

    }

}/////////////////////////////

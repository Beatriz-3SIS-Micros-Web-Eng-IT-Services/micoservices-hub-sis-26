package com.github.BeatrizRosa05.ms.pedidos.controller;

import com.github.BeatrizRosa05.ms.pedidos.dto.PedidoDTO;
import com.github.BeatrizRosa05.ms.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}/////////////////////////////

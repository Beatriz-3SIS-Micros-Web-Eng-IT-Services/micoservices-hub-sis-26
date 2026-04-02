package com.github.BeatrizRosa05.ms.pedidos.service;

import com.github.BeatrizRosa05.ms.pedidos.dto.PedidoDTO;
import com.github.BeatrizRosa05.ms.pedidos.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional(readOnly = true)
    public List<PedidoDTO> findAllPedidos(){
        return pedidoRepository.findAll().stream().map(PedidoDTO::new).toList();
    }


}////////////

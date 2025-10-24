package com.isac.agendadortarefas.controller;

import com.isac.agendadortarefas.business.TarefaService;
import com.isac.agendadortarefas.business.dto.TarefasDTO;
import com.isac.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefas(@RequestBody TarefasDTO dto,
                                                    @RequestHeader("Authorization") String token){

        return ResponseEntity.ok(tarefaService.gravarTarefa(dto, token));

    }
}

package com.isac.agendadortarefas.controller;

import com.isac.agendadortarefas.business.TarefaService;
import com.isac.agendadortarefas.business.dto.TarefaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaDTO> gravarTarefas(@RequestBody TarefaDTO dto,
                                                   @RequestHeader("Authorization") String token){

        return ResponseEntity.ok(tarefaService.gravarTarefa(dto, token));

    }

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefaDTO>> buscaListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal){

        return ResponseEntity.ok(tarefaService.buscaTarefasAgendadasPorPeriodo(dataInicial,dataFinal));
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> buscaTarefasAgendadasPorEmail(
            @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tarefaService.buscaTarefasAgendadasPorEmail(token));
    }
}

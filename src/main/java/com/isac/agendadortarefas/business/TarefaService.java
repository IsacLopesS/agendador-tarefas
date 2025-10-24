package com.isac.agendadortarefas.business;


import com.isac.agendadortarefas.business.dto.TarefaDTO;
import com.isac.agendadortarefas.business.mapper.TarefaConverter;
import com.isac.agendadortarefas.infrastructure.entity.TarefaEntity;
import com.isac.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.isac.agendadortarefas.infrastructure.repository.TarefaRepository;
import com.isac.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;

    public TarefaDTO gravarTarefa(TarefaDTO dto, String token){

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setEmailUsuario(email);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);

        TarefaEntity entity = tarefaConverter.paraTarefaEntity(dto);
        return tarefaConverter.paraTarefaDTO(tarefaRepository.save(entity));
    }

    public List<TarefaDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal){
        return  tarefaConverter.paraListTarefasDTO(tarefaRepository.findByDataEventoBetween(dataInicial,dataFinal));
    }

    public List<TarefaDTO> buscaTarefasAgendadasPorEmail(String token){

        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return  tarefaConverter.paraListTarefasDTO(tarefaRepository.findByEmailUsuario(email));
    }
}

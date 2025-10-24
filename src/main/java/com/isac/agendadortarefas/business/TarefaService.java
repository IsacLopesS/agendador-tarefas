package com.isac.agendadortarefas.business;


import com.isac.agendadortarefas.business.dto.TarefasDTO;
import com.isac.agendadortarefas.business.mapper.TarefaConverter;
import com.isac.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.isac.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.isac.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.isac.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefaService {
    private final TarefasRepository tarefasRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(TarefasDTO dto, String token){

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setEmailUsuario(email);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);

        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);
        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
    }
}

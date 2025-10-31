package com.isac.agendadortarefas.business;


import com.isac.agendadortarefas.business.dto.TarefaDTO;
import com.isac.agendadortarefas.business.mapper.TarefaConverter;
import com.isac.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.isac.agendadortarefas.infrastructure.entity.TarefaEntity;
import com.isac.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.isac.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
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
    private final TarefaUpdateConverter tarefaUpdateConverter;

    public TarefaDTO gravarTarefa(TarefaDTO dto, String token) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setEmailUsuario(email);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);

        TarefaEntity entity = tarefaConverter.paraTarefaEntity(dto);
        return tarefaConverter.paraTarefaDTO(tarefaRepository.save(entity));
    }

    public List<TarefaDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefaConverter.paraListTarefasDTO(tarefaRepository.findByDataEventoBetweenAndStatusNotificacaoEnum(dataInicial, dataFinal,StatusNotificacaoEnum.PENDENTE));
    }

    public List<TarefaDTO> buscaTarefasAgendadasPorEmail(String token) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return tarefaConverter.paraListTarefasDTO(tarefaRepository.findByEmailUsuario(email));
    }

    public void deletaTarefaPorId(String id) {
        try {
            tarefaRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("erro ao deletar tarefa por id, id, inesistente: " + id
                    + e.getCause());
        }
    }

    public TarefaDTO alteraStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefaEntity entity = tarefaRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("nao encontrado o id da tarefa: " + id));

            entity.setStatusNotificacaoEnum(status);
            return tarefaConverter.paraTarefaDTO(tarefaRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa ",e.getCause());
        }

    }

    public TarefaDTO atualizaTarefa(TarefaDTO dto, String id){
           try {

               TarefaEntity entity = tarefaRepository.findById(id).orElseThrow(()->
                       new ResourceNotFoundException("tarefa nao encontrada "+id));
               tarefaUpdateConverter.updateTarefa(dto, entity); // modifica a propria entity

               return tarefaConverter.paraTarefaDTO(tarefaRepository.save(entity));
           } catch (ResourceNotFoundException e) {
               throw new RuntimeException("Erro ao atualizar tarefa: ", e.getCause());
           }
    }


}

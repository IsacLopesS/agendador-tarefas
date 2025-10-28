package com.isac.agendadortarefas.business.mapper;

import com.isac.agendadortarefas.business.dto.TarefaDTO;
import com.isac.agendadortarefas.infrastructure.entity.TarefaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaConverter {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "dataEvento", target = "dataEvento")
    @Mapping(source = "dataCriacao", target = "dataCriacao")
    TarefaEntity paraTarefaEntity(TarefaDTO dto);

    TarefaDTO paraTarefaDTO(TarefaEntity entity);

    List<TarefaDTO> paraListTarefasDTO(List<TarefaEntity> tarefaEntity);
}

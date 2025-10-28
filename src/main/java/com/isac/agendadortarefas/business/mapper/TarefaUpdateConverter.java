package com.isac.agendadortarefas.business.mapper;

import com.isac.agendadortarefas.business.dto.TarefaDTO;
import com.isac.agendadortarefas.infrastructure.entity.TarefaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface TarefaUpdateConverter {

    void updateTarefa(TarefaDTO dto, @MappingTarget TarefaEntity entity);


}

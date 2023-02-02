package com.bootcamp.passiveProduct.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.bootcamp.passiveProduct.domain.Commision;
import com.bootcamp.passiveProduct.web.model.CommisionModel;

@Mapper(componentModel = "spring")
public interface CommisionMapper {
	
	CommisionMapper INSTANCE = Mappers.getMapper(CommisionMapper.class);

	//@Mapping(target="client.id", source="client.id")
	Commision modelToEntity(CommisionModel model);

	//@Mapping(target="client.id", source="client.id")
	CommisionModel entityToModel(Commision event);

	@Mapping(target = "id", ignore = true)
	void update(@MappingTarget Commision entity, Commision updateEntity);
	
}

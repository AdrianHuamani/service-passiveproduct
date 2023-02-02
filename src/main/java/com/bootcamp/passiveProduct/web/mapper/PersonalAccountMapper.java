package com.bootcamp.passiveProduct.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.bootcamp.passiveProduct.domain.PersonalAccount;
import com.bootcamp.passiveProduct.web.model.PersonalAccountModel;

@Mapper(componentModel = "spring")
public interface PersonalAccountMapper {
	
	PersonalAccountMapper INSTANCE = Mappers.getMapper(PersonalAccountMapper.class);

	//@Mapping(target="client.id", source="client.id")
	PersonalAccount modelToEntity(PersonalAccountModel model);

	//@Mapping(target="client.id", source="client.id")
	PersonalAccountModel entityToModel(PersonalAccount event);

	@Mapping(target = "id", ignore = true)
	void update(@MappingTarget PersonalAccount entity, PersonalAccount updateEntity);
	
}

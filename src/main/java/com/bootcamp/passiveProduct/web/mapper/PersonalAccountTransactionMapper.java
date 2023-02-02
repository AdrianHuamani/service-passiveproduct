package com.bootcamp.passiveProduct.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.bootcamp.passiveProduct.domain.PersonalAccountTransaction;
import com.bootcamp.passiveProduct.web.model.PersonalAccountTransactionModel;


@Mapper(componentModel="spring")
public interface PersonalAccountTransactionMapper {

	PersonalAccountTransactionMapper INSTANCE = Mappers.getMapper(PersonalAccountTransactionMapper.class);

	//@Mapping(target="client.id", source="client.id")
	PersonalAccountTransaction modelToEntity(PersonalAccountTransactionModel model);

	//@Mapping(target="client.id", source="client.id")
	PersonalAccountTransactionModel entityToModel(PersonalAccountTransaction event);

	@Mapping(target = "id", ignore = true)
	void update(@MappingTarget PersonalAccountTransaction entity, PersonalAccountTransaction updateEntity);
	
}

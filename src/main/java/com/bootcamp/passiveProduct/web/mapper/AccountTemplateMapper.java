package com.bootcamp.passiveProduct.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.bootcamp.passiveProduct.domain.AccountTemplate;
import com.bootcamp.passiveProduct.web.model.AccountTemplateModel;

@Mapper(componentModel = "spring")
public interface AccountTemplateMapper {
	
	AccountTemplateMapper INSTANCE = Mappers.getMapper(AccountTemplateMapper.class);

	//@Mapping(target="client.id", source="client.id")
	AccountTemplate modelToEntity(AccountTemplateModel model);

	//@Mapping(target="client.id", source="client.id")
	AccountTemplateModel entityToModel(AccountTemplate event);

	@Mapping(target = "id", ignore = true)
	void update(@MappingTarget AccountTemplate entity, AccountTemplate updateEntity);
	
}

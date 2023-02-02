package com.bootcamp.passiveProduct.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class CommisionModel {

	@JsonIgnore
	private String id;

	@NotBlank(message = "Commision Type cannot be null or empty")
	private String type; // MANTENIMIENTO

	@NotBlank(message = "Commision accountType cannot be null or empty")
//	@JsonProperty("Tipo de cuenta")
	private String accountType; // CORRIENTE

	private Double value;
	
	@JsonIgnore
	private String userRegisteringId;
	
	@JsonIgnore
	private LocalDate createDate;

	
	public CommisionModel() {
		this.userRegisteringId=System.getProperty("user.name");
		this.createDate=LocalDate.now();
	}

}

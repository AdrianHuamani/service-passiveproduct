package com.bootcamp.passiveProduct.web.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.bootcamp.passiveProduct.domain.PersonalAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalAccountTransactionModel {

	@JsonIgnore
	private String id;
	
	@NotBlank(message="sourceAccount cannot be null or empty")
	private String sourceAccount;
	
	@NotBlank(message="destinyAccount cannot be null or empty")
	private String destinyAccount;
	
	@NotBlank(message="destinyAccountType cannot be null or empty")
	private String destinyAccountType; //Otro banco - Mismo banco - Mis cuentas
	
	//@NotBlank(message="transactionDate cannot be null or empty")
	//private LocalDate transactionDate;
	
	@NotBlank(message="movementType cannot be null or empty")
	private String movementType; //Retiro - Deposito - Transferencia
	
	@NotBlank(message="Currrency cannot be null or empty")
	private String currency; //Moneda
	
	//@NotBlank(message="amount cannot be null or empty")
	private Double amount;

	//@NotBlank(message="commision cannot be null or empty")
	//private Double commision;
	
	//@NotBlank(message="totalAmount cannot be null or empty")
	//private Double totalAmount;
	
	@NotBlank(message="chanelTypeId cannot be null or empty")
	private String chanelTypeId;
}

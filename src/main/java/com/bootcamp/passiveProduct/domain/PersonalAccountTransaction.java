package com.bootcamp.passiveProduct.domain;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Document(value = "personalAccountTransaction")
public class PersonalAccountTransaction {

	@Id
	private String id;
	
	@NotNull
	private String sourceAccount;
	
	@NotNull
	private String destinyAccount;
	
	@NotNull
	private String destinyAccountType; //Otro banco - Mismo banco - Mis cuentas
	
	@NotNull
	private LocalDate transactionDate;
	
	@NotNull
	private String movementType; //Retiro - Deposito - Transferencia
	
	@NotNull
	private String currency; //Moneda
	
	@NotNull
	private Double amount;
	
	@NotNull
	private Double commision;
	
	@NotNull
	private Double totalAmount;
	
	@NotNull
	private String chanelTypeId;
	
	
}

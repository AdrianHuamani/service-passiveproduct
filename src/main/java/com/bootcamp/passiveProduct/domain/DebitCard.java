package com.bootcamp.passiveProduct.domain;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
@Document(value = "accountTemplate")
public class DebitCard {

	@Id
	private String id;
	
	@NotNull
	@Indexed(unique = true)
	private String accountType; 
	
	@NotNull
	private Double availableBalance; //
	
	@NotNull
	private Double balance;
	
	@NotNull
	private String userRegisteringId;
	
	@NotNull
	private String currency;
	
	@NotNull
	private Integer movementQuantityMax;
	
	@NotNull
	private String movementFrecuency;
	
	@NotNull
	private LocalDate movementDate;
	
	@NotNull
	private LocalDate createDate;

	public DebitCard() {
		this.userRegisteringId=System.getProperty("user.name");
		this.createDate=LocalDate.now();
	}
}

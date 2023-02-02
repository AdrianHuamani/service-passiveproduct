package com.bootcamp.passiveProduct.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
@Document(value = "personalAccount")
public class PersonalAccount {
	
	
	@Id
	private String id;
	
	@NotNull
	private String accountType;
	
	@NotNull
	@Indexed(unique = true)
	private String accountNumber;
	
	@NotNull
	@Indexed(unique = true)
	private String cci;
	
	@NotNull
	private Double availableBalance;
	
	@NotNull
	private Double balance;
	
	@NotNull
	private String userRegisteringId;
	
	@NotNull
	private String currency;
	
	@NotNull
	private Integer movementQuantity;
	
	@NotNull
	private Integer movementQuantityMax;
	
	@NotNull
	private String movementFrecuency;
	
	@NotNull
    private String identityNumberClient; 

    @NotNull
    private String identityTypeClient; 
	
	@NotNull
	private LocalDate openingDate;
	
	@NotNull
	private List<PersonalAccountTransaction> transactions= new ArrayList();
	
	public void addTransaction(PersonalAccountTransaction personalAccountTransaction) {
		this.transactions.add(personalAccountTransaction);
	}
	
	public PersonalAccount() {
		this.userRegisteringId=System.getProperty("user.name");
		this.openingDate=LocalDate.now();
		this.movementQuantity=1;
	}
	
}

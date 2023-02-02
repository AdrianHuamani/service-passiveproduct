package com.bootcamp.passiveProduct.domain;


import lombok.*;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@ToString
@AllArgsConstructor
@Document(value = "commision")

public class Commision {
	
	public Commision() {
		this.userRegisteringId=System.getProperty("user.name");
		this.createDate=LocalDate.now();
	}
	
	@Id
    private String id;

    @NotNull
    private String type; //MANTENIMIENTO

    @NotNull
    private String accountType; //CORRIENTE

    @NotNull
    private Double value;
    
	@NotNull
	private String userRegisteringId;
	
	@NotNull
	private LocalDate createDate;

}

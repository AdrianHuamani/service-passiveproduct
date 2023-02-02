package com.bootcamp.passiveProduct.domain;


import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private String id;

    private String identityNumber;

    private IdentityType identityType;

    private String name;

    private String lastName;

    private String businessName;

    private String email;

    private String phoneNumber;

    private LocalDate birthday;

    private ClientType clientType;

}

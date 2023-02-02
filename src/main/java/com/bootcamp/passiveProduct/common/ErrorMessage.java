package com.bootcamp.passiveProduct.common;

public enum ErrorMessage {
    CLIENT_NOT_FOUND("El cliente no existe"),
    CURRENCY_NOT_SUPPORTED("Moneda no soportada por la cuenta"),
    ACCOUNT_DESTINY_NOT_FOUND("La cuenta destino no existe"),
    ACCOUNT_ORIGIN_NOT_FOUND("La cuenta origen no existe"),
    DEBITCARD_NOT_FOUND("La TD no existe"),
    INSUFFICIENTE_BALANCE("Saldo insuficiente para la operación"),
    TRANSACTION_NOT_SUPPORTED("No puede enviar dinero a otros bancos"),
    CURRENCY_OR_ACCOUNTTYPE_NOT_FOUND("Moneda o tipo de cuenta incorrecto"),
    NO_TRANSACTION_THIS_DAY("No puede realizar transacciones en esta fecha"),
    EXCEED_AMOUNT_OF_MOVES("Excedio la cantidad de movimientos");
	

	private String value;
    ErrorMessage(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}

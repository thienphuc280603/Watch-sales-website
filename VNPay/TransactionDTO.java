package com.poly.VNPay;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO implements Serializable{
	private String status;
	private String message;
	private String data;
}

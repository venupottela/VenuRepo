package com.poc.onpassive.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class ResponseData {

	private Date timeStamp;
	private Integer statusCode;
	private String status;
	private String message;
	private List<?> response;


public ResponseData(Date timeStamp, Integer statusCode, String status, String message, List<?> response) {
	this.timeStamp = timeStamp;
	this.statusCode = statusCode;
	this.status = status;
	this.message = message;
	this.response = response;
}


public ResponseData(Date timeStamp, Integer statusCode, String status, String message) {
	super();
	this.timeStamp = timeStamp;
	this.statusCode = statusCode;
	this.status = status;
	this.message = message;
}


public ResponseData(Integer statusCode, String status, String message, List<?> response) {
	super();
	this.statusCode = statusCode;
	this.status = status;
	this.message = message;
	this.response = response;
}


public ResponseData(Integer statusCode, String status, String message) {
	super();
	this.statusCode = statusCode;
	this.status = status;
	this.message = message;
}










	
	

}

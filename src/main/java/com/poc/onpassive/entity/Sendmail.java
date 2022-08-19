package com.poc.onpassive.entity;

import lombok.Data;

@Data
//@Entity
//@Table(name = "sendmail")
//@NoArgsConstructor
public class Sendmail {

	private String to;
	private String textBody;
	private String topic;
	private String setFrom;

}

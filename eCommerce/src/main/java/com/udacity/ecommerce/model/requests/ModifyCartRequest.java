package com.udacity.ecommerce.model.requests;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyCartRequest {

	@JsonProperty
	private long itemId;

	@JsonProperty
	private String username;

	@JsonProperty
	private int quantity;

}

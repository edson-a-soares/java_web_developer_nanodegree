package com.udacity.ecommerce.model.persistence;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	@Column
	private Long id;
	
	@ManyToMany
	@JsonProperty
	@Column
    private List<Item> items;
	
	@OneToOne(mappedBy = "cart")
	@JsonProperty
    private User user;
	
	@Column
	@JsonProperty
	private BigDecimal total;

	public void addItem(Item item) {
		if(items == null)
			items = new ArrayList<>();

		items.add(item);
		if(total == null)
			total = new BigDecimal(0);

		total = total.add(item.getPrice());
	}
	
	public void removeItem(Item item) {
		if(items == null)
			items = new ArrayList<>();

		items.remove(item);
		if(total == null)
			total = new BigDecimal(0);

		total = total.subtract(item.getPrice());
	}
}

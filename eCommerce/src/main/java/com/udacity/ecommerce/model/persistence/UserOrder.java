package com.udacity.ecommerce.model.persistence;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_order")
public class UserOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	@Column
	private Long id;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonProperty
	@Column
    private List<Item> items;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable = false, referencedColumnName = "id")
	@JsonProperty
    private User user;
	
	@JsonProperty
	@Column
	private BigDecimal total;

	public static UserOrder createFromCart(Cart cart) {
		UserOrder order = new UserOrder();

		order.setUser(cart.getUser());
		order.setTotal(cart.getTotal());
		order.setItems(new ArrayList<>(cart.getItems()));

		return order;
	}
	
}

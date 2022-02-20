package de.tomsplayground.lemon;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.hc.core5.http.ParseException;

public class Main {
	
	public static void main(String[] args) throws IOException, ParseException {
		String apiKey = args[0];
		String spaceId = "sp_qyDZLFF77V0NR5WwXHTP3yDm2hpJfbC8VF";
		
		// Init
		LemonRequest lemonRequest = new LemonRequest();
		lemonRequest.init(apiKey);
		
		// List spaces
		for (Space space : lemonRequest.spaceList()) {
			System.out.println("Space: "+space);
		}
		
		// List orders
		List<Order> orderList = lemonRequest.orderList(spaceId);
		for (Order order : orderList) {
			System.out.println("Order: "+order);
		}
		
		// Activate
		lemonRequest.activateOrder(orderList.get(0).id, "1234");
		
		// Place order
		Order order = new Order();
		order.isin = "US1912161007";
		order.spaceId = spaceId;
		order.expiresAt = LocalDate.now().plus(1, ChronoUnit.DAYS).atStartOfDay();
		order.side = "buy";
		// 500.0000
		order.stopPrice = 500000;
		// 530.0000
		order.limitPrice = 550000;
		order.quantity = 1;
		order.venue = "XMUN";

		lemonRequest.placeOrder(order);
	}

}

package de.tomsplayground.lemon;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class OrderTest {

	private JsonMapper mapper = new JsonMapper();

	@Test
	public void orderAnswer() throws IOException {
		InputStream stream = SpaceTest.class.getResourceAsStream("/orderAnswer.json");
		String orderJson = IOUtils.toString(stream, StandardCharsets.UTF_8);

		Order order = mapper.getMapper().readValue(orderJson, Order.class);

		assertEquals("US19260Q1076", order.isin);
		assertEquals(LocalDateTime.of(2022, 2, 25, 22, 59), order.expiresAt);
	}

	@Test
	public void jsonOrderRequest() throws IOException {
		Order order = new Order();
		String orderJson1 = mapper.getMapper().writeValueAsString(order);

		InputStream stream = SpaceTest.class.getResourceAsStream("/orderRequest.json");
		String orderJson2 = IOUtils.toString(stream, StandardCharsets.UTF_8)
				.replaceAll("\\t+","").replaceAll("\\n", "").replaceAll(": ", ":");

		assertEquals(orderJson2, orderJson1);
	}

	@Test
	public void parseOrderList() throws IOException {
		InputStream stream = SpaceTest.class.getResourceAsStream("/orderListAnswer.json");
		String ordersJson = IOUtils.toString(stream, StandardCharsets.UTF_8);

		OrderList orderList = mapper.getMapper().readValue(ordersJson, OrderList.class);
		
		assertEquals(1, (int) orderList.total);
		Order order = orderList.results.get(0);
		assertEquals("US19260Q1076", order.isin);
		assertEquals(5300000, (int)order.limitPrice);
		assertEquals(new BigDecimal("530.0000"), order.getLimitPriceDecimal());
	}

}

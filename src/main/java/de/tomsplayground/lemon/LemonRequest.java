package de.tomsplayground.lemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LemonRequest {
	
	private static final Logger log = LoggerFactory.getLogger(LemonRequest.class);

	private static final String BASE_URL = "https://paper-trading.lemon.markets/v1";
	
	private CloseableHttpClient httpclient;
	
	private JsonMapper jsonMapper = new JsonMapper();
	
	public void init(String apiKey) {
		List<Header> headers = new ArrayList<>();
		headers.add(new BasicHeader(HttpHeaders.AUTHORIZATION, apiKey));
		httpclient = HttpClients.custom().setDefaultHeaders(headers).build();
		
	}

	public List<Space> spaceList() throws IOException, ParseException {
		HttpGet request = new HttpGet(BASE_URL+"/spaces");
		
		try (CloseableHttpResponse response1 = httpclient.execute(request)) {
			log.debug(response1.getCode() + " " + response1.getReasonPhrase());
			HttpEntity entity1 = response1.getEntity();
			String answer = EntityUtils.toString(entity1);
			log.debug(answer);
			SpaceList spaceList = jsonMapper.getMapper().readValue(answer, SpaceList.class);
			return spaceList.results;
		}		
	}
	
	public List<Order> orderList(String spaceId) throws IOException, ParseException {
		HttpGet request = new HttpGet(BASE_URL+"/orders?space_id="+spaceId);
		
		try (CloseableHttpResponse response1 = httpclient.execute(request)) {
			log.debug(response1.getCode() + " " + response1.getReasonPhrase());
			HttpEntity entity1 = response1.getEntity();
			String answer = EntityUtils.toString(entity1);
			log.debug(answer);
			OrderList orderList = jsonMapper.getMapper().readValue(answer, OrderList.class);
			return orderList.results;
		}
	}
	
	public void placeOrder(Order order) throws IOException, ParseException {
		String orderJson = jsonMapper.getMapper().writeValueAsString(order);

		HttpPost request = new HttpPost(BASE_URL+"/orders/");
		request.setEntity(new StringEntity(orderJson, ContentType.APPLICATION_JSON));		

		try (CloseableHttpResponse response1 = httpclient.execute(request)) {
			log.debug(response1.getCode() + " " + response1.getReasonPhrase());
			HttpEntity entity1 = response1.getEntity();
			String answer = EntityUtils.toString(entity1);
			log.info(answer);
		}
	}
	
	public void activateOrder(String orderId, String pin) throws IOException, ParseException {
		HttpPost request = new HttpPost(BASE_URL+"/orders/"+orderId+"/activate/");
		request.setEntity(new StringEntity("{\"pin\": "+pin+"}", ContentType.APPLICATION_JSON));		

		try (CloseableHttpResponse response1 = httpclient.execute(request)) {
			log.debug(response1.getCode() + " " + response1.getReasonPhrase());
			HttpEntity entity1 = response1.getEntity();
			String answer = EntityUtils.toString(entity1);
			log.info(answer);
		}
	}

}

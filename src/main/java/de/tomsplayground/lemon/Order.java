package de.tomsplayground.lemon;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

	String id;
	String isin;
	@JsonProperty("expires_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	LocalDateTime expiresAt;
	String side;
	@JsonProperty("stop_price")
	Integer stopPrice;
	@JsonProperty("limit_price")
	Integer limitPrice;
	Integer quantity;
	String venue;
	@JsonProperty("space_id")
	String spaceId;
	String notes;

	public Order() {
		isin = "US0378331005";
		expiresAt = LocalDate.parse("2022-02-25").atStartOfDay();
		side = "buy";
		// 500.0000
		stopPrice = 5000000;
		// 530.0000
		limitPrice = 5300000;
		quantity = 1;
		venue = "XMUN";
		spaceId = "1234567";
		notes = "I want to attach a note to this order";
	}

	@JsonIgnore
	public BigDecimal getLimitPriceDecimal() {
		return new BigDecimal(limitPrice).movePointLeft(4);
	}

	@JsonIgnore
	public BigDecimal getStopPriceDecimal() {
		return new BigDecimal(stopPrice).movePointLeft(4);
	}

	@JsonGetter("expires_at")
	public String getExpiresAtJson() {
		return expiresAt.toLocalDate().toString();
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}

package de.tomsplayground.lemon;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Space {

	String id;
	String name;
	String description;
	@JsonProperty("risk_limit")
	Long riskLimit;
	@JsonProperty("buying_power")
	Long buyingPower;
	Long earnings;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	public BigDecimal getRiskLimitDecimal() {
		return new BigDecimal(riskLimit).movePointLeft(4);
	}

	public BigDecimal getBuyingPowerDecimal() {
		return new BigDecimal(buyingPower).movePointLeft(4);
	}

	public BigDecimal getEarningsDecimal() {
		return new BigDecimal(earnings).movePointLeft(4);
	}
}

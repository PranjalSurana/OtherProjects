package com.fidelity.investmonkey.models;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Price 
{
	 private double askPrice;
	    private double bidPrice;
	    private String priceTimestamp;
	    private Instrument instrument;
	    
	    
		public Price(double askPrice, double bidPrice, String priceTimestamp, Instrument instrument) {
			super();
			this.askPrice = askPrice;
			this.bidPrice = bidPrice;
			this.priceTimestamp = priceTimestamp;
			this.instrument = instrument;
		}
		public double getAskPrice() {
			return askPrice;
		}
		public void setAskPrice(double askPrice) {
			this.askPrice = askPrice;
		}
		public double getBidPrice() {
			return bidPrice;
		}
		public void setBidPrice(double bidPrice) {
			this.bidPrice = bidPrice;
		}
		public String getPriceTimestamp() {
			return priceTimestamp;
		}
		public void setPriceTimestamp(String priceTimestamp) {
			this.priceTimestamp = priceTimestamp;
		}
		public Instrument getInstrument() {
			return instrument;
		}
		public void setInstrument(Instrument instrument) {
			this.instrument = instrument;
		}
		@Override
		public int hashCode() {
			return Objects.hash(askPrice, bidPrice, instrument, priceTimestamp);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Price other = (Price) obj;
			return Objects.equals(askPrice, other.askPrice) && Objects.equals(bidPrice, other.bidPrice)
					&& Objects.equals(instrument, other.instrument)
					&& Objects.equals(priceTimestamp, other.priceTimestamp);
		}
		@Override
		public String toString() {
			return "Price [askPrice=" + askPrice + ", bidPrice=" + bidPrice + ", priceTimestamp=" + priceTimestamp
					+ ", instrument=" + instrument + "]";
		}
}

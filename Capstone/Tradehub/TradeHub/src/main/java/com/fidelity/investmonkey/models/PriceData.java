package com.fidelity.investmonkey.models;

import java.util.ArrayList;
import java.util.List;

public class PriceData 
{
	private List<Price> priceList;

    public List<Price> getPriceList() {
        return priceList;
    }

    public PriceData() {
		super();
		this.priceList = new ArrayList<Price>();
	}

	public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    @Override
    public String toString() {
        return "PriceData{" +
                "priceList=" + priceList +
                '}';
    }
}

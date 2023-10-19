package com.fidelity.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fidelity.business.Gadget;
import com.fidelity.business.Widget;
import com.fidelity.integration.mapper.WarehouseMapper;

@Repository("warehouseDao")
public class WarehouseDAOMyBatisImpl {
	@Autowired
	private WarehouseMapper mapper;
		
	public List<Widget> getAllWidgets() {
		List<Widget> products = null;

		products = mapper.getAllWidgets();
		
		return products;
	}

	public List<Gadget> getAllGadgets() {
		List<Gadget> products = null;

		products = mapper.getAllGadgets();
		
		return products;
	}

	public void insertWidget(Widget widget) {
		mapper.insertWidget(widget);
	}

	public void insertGadget(Gadget gadget) {
		mapper.insertGadget(gadget);
	}
}

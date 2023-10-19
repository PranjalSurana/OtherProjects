package com.roifmr.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.roifmr.business.Gadget;
import com.roifmr.business.Widget;

@Repository("warehouseDao")
public class WarehouseDaoStubImpl implements WarehouseDao {

	private static Map<Integer, Widget> widgets = Map.of(
		1, new Widget(1, "Low Impact Widget", 12.99, 2, 3),
		2, new Widget(2, "Medium Impact Widget", 42.99, 5, 5),
		3, new Widget(3, "High Impact Widget", 89.99, 10, 8)
	);
		
	@Override
	public List<Widget> getAllWidgets() {
        return new ArrayList<>(widgets.values());
	}
	
	@Override
	public Widget getWidget(int id) {
		return widgets.get(id);
	}

	@Override
	public int deleteWidget(int id) {
		int count = 0;
		if (widgets.keySet().contains(id)) {
			widgets.remove(id);
			count = 1;
		}
		return count;
	}

	@Override
	public int insertWidget(Widget widget) {
		int count = 0;
		if (!widgets.keySet().contains(widget.getId())) {
			widgets.put(widget.getId(), widget);
			count = 1;
		}
		return count;
	}

	@Override
	public int updateWidget(Widget widget) {
		int count = 0;
		if (widgets.keySet().contains(widget.getId())) {
			widgets.put(widget.getId(), widget);
			count = 1;
		}
		return count;
	}

	// Gadget methods

	@Override
	public List<Gadget> getAllGadgets() {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public Gadget getGadget(int id) {
		throw new UnsupportedOperationException("not yet implemented");
	}
	
	@Override
	public int deleteGadget(int id) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public int insertGadget(Gadget gadget) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public int updateGadget(Gadget gadget) {
		throw new UnsupportedOperationException("not yet implemented");
	}

}

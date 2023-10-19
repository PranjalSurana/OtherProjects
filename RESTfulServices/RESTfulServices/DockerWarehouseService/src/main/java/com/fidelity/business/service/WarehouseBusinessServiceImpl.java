package com.fidelity.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.business.Gadget;
import com.fidelity.business.Widget;
import com.fidelity.integration.WarehouseDao;

/**
 * The business service that manipulates Widgets and Gadgets 
 * in the warehouse.
 * 
 * @author ROI Instructor
 *
 */
@Service
public class WarehouseBusinessServiceImpl implements WarehouseBusinessService {
	@Autowired
	private WarehouseDao dao;

	// ***** Widget Methods *****
	@Override
	public List<Widget> findAllWidgets() {
		List<Widget> widgets;
		
		try {
			widgets = dao.getAllWidgets();
		} catch (Exception e) {
			String msg = "Error querying all Widgets in the Warehouse database.";
			throw new WarehouseDatabaseException(msg, e);
		}
		
		return widgets;
	}

	@Override
	public Widget findWidgetById(int id) {
		Widget widget = null;

		try {
			widget = dao.getWidget(id);
		} catch (Exception e) {
			String msg = String.format("Error querying For Widget with id = %d in the Warehouse database.", id);
			throw new WarehouseDatabaseException(msg, e);
		}
		
		return widget;
	}

	@Override
	public int removeWidget(int id) {
		int count = 0;
		
		try {
			count = dao.deleteWidget(id);
		} catch (Exception e) {
			String msg = String.format("Error removing Widget with id = %d the Warehouse database.", id);
			throw new WarehouseDatabaseException(msg, e);
		}
		
		return count;
	}

	@Override
	public int addWidget(Widget widget) {
		int count = 0;
		
		try {
			count = dao.insertWidget(widget);
		} catch (Exception e) {
			String msg = "Error inserting Widget into the Warehouse database.";
			throw new WarehouseDatabaseException(msg, e);
		}

		return count;
	}

	@Override
	public int modifyWidget(Widget widget) {
		int count = 0;
		
		try {
			count = dao.updateWidget(widget);
		} catch (Exception e) {
			String msg = "Error updating Widget in the Warehouse database.";
			throw new WarehouseDatabaseException(msg, e);
		}

		return count;
	}

	// ***** Gadget Methods *****
	@Override
	public List<Gadget> findAllGadgets() {
		List<Gadget> gadgets = null;
		
		try {
			gadgets = dao.getAllGadgets();
		} catch (Exception e) {
			String msg = "Error querying for all Gadgets in the Warehouse database.";
			throw new WarehouseDatabaseException(msg, e);
		}
		
		return gadgets;
	}

	@Override
	public Gadget findGadgetById(int id) {
		Gadget gadget = null;
		
		try {
			gadget = dao.getGadget(id);
		} catch (Exception e) {
			String msg = String.format("Error querying for Gadget with id = %d in the Warehouse database.", id);
			throw new WarehouseDatabaseException(msg, e);
		}

		return gadget;
	}

	
	@Override
	public int removeGadget(int id) {
		int count = 0;
		
		try {
			count = dao.deleteGadget(id);
		} catch (Exception e) {
			String msg = "Error removing Gadget in the Warehouse database.";
			throw new WarehouseDatabaseException(msg, e);
		}
		
		return count;
	}

	@Override
	public int addGadget(Gadget gadget) {
		int count = 0;
		
		try {
			count = dao.insertGadget(gadget);
		} catch (Exception e) {
			String msg = "Error inserting Gadget into the Warehouse database.";
			throw new WarehouseDatabaseException(msg, e);
		}
		
		return count;
	}

	@Override
	public int modifyGadget(Gadget gadget) {
		int count = 0;
		
		try {
			count = dao.updateGadget(gadget);
		} catch (Exception e) {
			String msg = "Error updating Gadget in the Warehouse database.";
			throw new WarehouseDatabaseException(msg, e);
		}

		return count;
	}

	
}

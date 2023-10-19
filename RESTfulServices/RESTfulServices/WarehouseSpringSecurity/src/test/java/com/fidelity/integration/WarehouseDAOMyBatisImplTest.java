package com.fidelity.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.business.Gadget;
import com.fidelity.business.Widget;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class WarehouseDAOMyBatisImplTest {
	@Autowired
	private WarehouseDAOMyBatisImpl warehouse;

	private Widget widget;
	private Gadget gadget;

	@BeforeEach
	void setUp() {
		widget = new Widget(1, "Low Impact Widget", new BigDecimal("12.99"), 2, 3);
		gadget = new Gadget(1, "Two Cylinder Gadget", new BigDecimal("19.99"), 2);
	}

	// Widget tests
	@Test
	public void testGetAllTheWidgets() {
		List<Widget> widgets = warehouse.getAllWidgets();
		
		assertNotNull(widgets);
		for (Widget widget : widgets) {
			assertNotNull(widget);
		}
		assertTrue(widgets.contains(widget));
	}
	
	@Test
	public void testInsertWidget() {
		Widget newWidget = new Widget(99, "Test Widget", new BigDecimal("42.0"), 2, 40);
		List<Widget> widgets = warehouse.getAllWidgets();
		int originalWidgetCount = widgets.size();
		
		warehouse.insertWidget(newWidget);
		
		widgets = warehouse.getAllWidgets();
		int afterInsertWidgetCount = widgets.size();
		
		assertEquals(originalWidgetCount + 1, afterInsertWidgetCount);
		assertTrue(widgets.contains(newWidget));
	}
		
	// Gadget tests
	@Test
	public void testGetAllTheGadgets() {
		List<Gadget> gadgets = warehouse.getAllGadgets();
		
		assertNotNull(gadgets);
		for (Gadget gadget : gadgets) {
			assertNotNull(gadget);
		}
		assertTrue(gadgets.contains(gadget));
	}


	@Test
	public void testInsertGadget() {
		Gadget newGadget = new Gadget(99, "Test Gadget", new BigDecimal("42.0"), 42);
		List<Gadget> gadgets = warehouse.getAllGadgets();
		int originalGadgetCount = gadgets.size();
		
		warehouse.insertGadget(newGadget);
		
		gadgets = warehouse.getAllGadgets();
		int afterInsertGadgetCount = gadgets.size();
		
		assertEquals(originalGadgetCount + 1, afterInsertGadgetCount);
		assertTrue(gadgets.contains(newGadget));
	}

}

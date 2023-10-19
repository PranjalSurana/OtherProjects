package com.fidelity.restcontroller;

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
import com.fidelity.restcontroller.WarehouseController;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
class WarehouseControllerTest {
	@Autowired
	private WarehouseController service;
	
	private Widget widget;
	private Gadget gadget;

	@BeforeEach
	void setUp() {
		widget = new Widget(1, "Low Impact Widget", new BigDecimal("12.99"), 2, 3);
		gadget = new Gadget(1, "Two Cylinder Gadget", new BigDecimal("19.99"), 2);
	}
	
	@Test
	void testQueryAllWidgets() {
		List<Widget> widgets = service.queryAllWidgets();
		
		assertNotNull(widgets);
		assertEquals(3, widgets.size());
		for (Widget widget : widgets) {
			assertNotNull(widget);
		}
		assertTrue(widgets.contains(widget));
	}

	@Test
	void testAddWidget() {
		Widget newWidget = new Widget(99, "Test Widget", new BigDecimal("42.0"), 2, 40);
		List<Widget> widgets = service.queryAllWidgets();
		int originalWidgetCount = widgets.size();
		
		service.addWidget(newWidget);
		
		widgets = service.queryAllWidgets();
		int afterInsertWidgetCount = widgets.size();
		
		assertEquals(originalWidgetCount + 1, afterInsertWidgetCount);
		assertTrue(widgets.contains(newWidget));
	}

}

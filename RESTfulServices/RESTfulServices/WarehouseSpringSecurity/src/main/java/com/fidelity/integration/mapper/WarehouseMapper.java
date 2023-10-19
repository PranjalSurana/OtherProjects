package com.fidelity.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fidelity.business.Gadget;
import com.fidelity.business.Widget;

@Mapper
public interface WarehouseMapper {
	List<Widget> getAllWidgets();
	void insertWidget(Widget widget);
	List<Gadget> getAllGadgets();
	void insertGadget(Gadget gadget);
}

package com.fidelity.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fidelity.business.Exhibit;

@Repository("exhibitsDao")
public class ExhibitDaoMyBatisImpl {
	@Autowired
	private ExhibitMapper mapper;

	public List<Exhibit> getAllExhibits() {		
		return mapper.getAllExhibits();
	}
}

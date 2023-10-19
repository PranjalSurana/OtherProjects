package com.fidelity.integration;

import java.util.List;

import com.fidelity.business.Exhibit;

/*
 * Data Access Object (DAO) interface for creating and updating exhibits.
 */
public interface ExhibitsDao {

	/* The list of exhibits found by DAO. This list will be empty (not null)
	 *  if there was an error. */
	public List<Exhibit> getExhibits();

	/* dispose of any resources */
	public void close();
}

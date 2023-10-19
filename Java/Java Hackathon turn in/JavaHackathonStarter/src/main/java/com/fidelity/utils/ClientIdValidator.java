package com.fidelity.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * ClientIdValidator validates an AccountPortal's client ID value.
 * 
 * @author ROI Instructor Team
 */
public class ClientIdValidator {
	public static final String ID_REG_EXP = "^[\\w-]{3,36}$";
	
	public static final String ERROR_MSG = 
		"The client ID must be between 3 and 36 characters long; " +
		"it may contain only letters, numbers, underscores, and dashes; " +
		"and it must be unique among all banking portals"; 

	private static final Set<String> usedIds = new HashSet<>();
	
	/**
	 * Validate an account portal client ID.
	 * 
	 * @param id a client ID
	 * @return true if the ID is unique and valid, otherwise false
	 */
	public boolean isValid(String id) {
		boolean valid = id != null && !usedIds.contains(id) && id.matches(ID_REG_EXP);
		if (valid) {
			usedIds.add(id);
		}
		return valid;
	}
}

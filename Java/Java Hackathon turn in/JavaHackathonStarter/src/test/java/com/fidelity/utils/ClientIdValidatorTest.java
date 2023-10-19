package com.fidelity.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * ClientIdValidatorTest defines unit tests for ClientIdValidator.
 * 
 * @author ROI Instructor Team
 */
class ClientIdValidatorTest {
	private ClientIdValidator validator;
	
	@BeforeEach
	public void setUp() {
		validator = new ClientIdValidator();
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { 
		"  ", "a", "ab", "1", "12", "ab#de", "ab de",  
		"1234567890123456789012345678901234567"
	})
	void isValid_False(String id) {
		assertFalse(validator.isValid(id));
	}

	@ParameterizedTest
	@ValueSource(strings = { 
		"abc", "abcd", "ab_de", "a12", "1abcde", "A123", "Z54_jb_15", "_BCDEF", 
		"123456789012345678901234567890123456",
		"480c05d8-c7bb-40d3-af82-67522fd32721"
	})
	void isValid_True(String id) {
		assertTrue(validator.isValid(id));
	}
	
	@Test
	void isValid_DuplicateId_False() {
		assertTrue(validator.isValid("client123"));
		assertFalse(validator.isValid("client123"));
	}
}

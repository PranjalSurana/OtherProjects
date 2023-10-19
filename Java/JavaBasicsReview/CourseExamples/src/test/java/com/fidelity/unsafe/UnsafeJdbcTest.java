package com.fidelity.unsafe;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnsafeJdbcTest {
	
	private UnsafeJdbc dao;
	
	@BeforeEach
	void setUp() {
		dao = new UnsafeJdbc();
	}

	@AfterEach
	void tearDown() {
		dao.close();
	}

	private void checkPermissionsForUserUnsafe(String user, List<Permission> expected) {
		List<Permission> actual = dao.queryPermissionsByUserUnsafe(user);
		assertNotNull(actual);
		assertEquals(expected.size(), actual.size());
		for (Permission perm : expected) {
			assertTrue(actual.contains(perm), user + " should have permission " + perm);
		}
	}

	@Test
	void testPermissionsCoddUnsafe() {
		List<Permission> expected = Arrays.asList(
			new Permission("superuser"),
			new Permission("admin")
		);
		checkPermissionsForUserUnsafe("codd", expected);
	}


	@Test
	void testPermissionsDateUnsafe() {
		List<Permission> expected = Arrays.asList(
			new Permission("readonly")
		);
		checkPermissionsForUserUnsafe("date", expected);
	}

	@Test
	void testPermissionsDateUnsafeInjection() {
		List<Permission> expected = Arrays.asList(
			new Permission("superuser"),
			new Permission("admin"),
			new Permission("readonly")
		);
		// date should have only 1 record, but this test expects all of them because the
		// injection returns all the permissions in the table
		checkPermissionsForUserUnsafe("date' OR '1' = '1", expected);
	}

	private void checkPermissionsForUserSafe(String user, List<Permission> expected) {
		List<Permission> actual = dao.queryPermissionsByUserSafe(user);
		assertNotNull(actual);
		assertEquals(expected.size(), actual.size());
		for (Permission perm : expected) {
			assertTrue(actual.contains(perm), user + " should have permission " + perm);
		}
	}

	@Test
	void testPermissionsCoddSafe() {
		List<Permission> expected = Arrays.asList(
			new Permission("superuser"),
			new Permission("admin")
		);
		checkPermissionsForUserSafe("codd", expected);
	}


	@Test
	void testPermissionsDateSafe() {
		List<Permission> expected = Arrays.asList(
			new Permission("readonly")
		);
		checkPermissionsForUserSafe("date", expected);
	}

	@Test
	void testPermissionsDateSafeInjection() {
		List<Permission> expected = Collections.<Permission>emptyList();
		// date should have 1 record, but this test checks for an empty list because
		// the parameter is treated as the whole name and, therefore, doesn't match anyone
		checkPermissionsForUserSafe("date' OR '1' = '1", expected);
	}

}

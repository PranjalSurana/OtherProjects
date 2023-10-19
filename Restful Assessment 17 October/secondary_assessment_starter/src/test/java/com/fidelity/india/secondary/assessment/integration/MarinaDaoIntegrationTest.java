package com.fidelity.india.secondary.assessment.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MarinaDaoIntegrationTest {

    @Autowired
    private MarinaDao dao;

    @Test
    public void getPortName_Success() {
        String expectedPortName = "Kanyakumari";

        String actualPortName = dao.getMarinaName("Nitrogen");

        assertEquals(expectedPortName, actualPortName);
    }

    @Test
    public void getPortNameWithAllLowerCase_Success() {
        String expectedPortName = "Kanyakumari";

        String actualPortName = dao.getMarinaName("nitrogen");

        assertEquals(expectedPortName, actualPortName);
    }

    @Test
    public void getPortNameWithAllUpperCase_Success() {
        String expectedPortName = "Kanyakumari";

        String actualPortName = dao.getMarinaName("NITROGEN");

        assertEquals(expectedPortName, actualPortName);
    }

    @Test
    public void getPortNameWithRandomCases_Success() {
        String expectedPortName = "Kanyakumari";

        String actualPortName = dao.getMarinaName("NiTRogEN");

        assertEquals(expectedPortName, actualPortName);
    }

    @Test
    public void getPortName_NotFound() {
        String actualPortName = dao.getMarinaName("notExistingVesselName");
        assertNull(actualPortName);
    }

    @Test
    public void getPortName_EmptyVesselName_Exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.getMarinaName("");
        });
    }

    @Test
    public void getMarinaName_NullVesselName_Exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.getMarinaName(null);
        });
    }
    
}
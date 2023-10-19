package com.roifmr.presidents.restservices;

import com.roifmr.presidents.integration.PresidentDao;
import com.roifmr.presidents.models.President;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class PresidentBusinessServicePojoUnitTest {

    @Mock
    private PresidentDao presidentDao;

    @InjectMocks
    private PresidentBusinessServiceImpl presidentBusinessService;

    List<President> presidentList;

    President president1 = new President(1, "f1", "l1", 1789, 1795, "img1.jpg", "bio1");
    President president2 = new President(2, "f2", "l2", 1789, 1795, "img2.jpg", "bio2");
    President president3 = new President(3, "f3", "l3", 1789, 1795, "img3.jpg", "bio3");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllPresidents() {
        presidentList = Arrays.asList(president1, president2, president3);
        when(presidentDao.queryAllPresidents()).thenReturn(presidentList);
        assertEquals(presidentList, presidentBusinessService.findAllPresidents());
    }

    @Test
    void testFindAllPresidents_DaoReturnsEmptyList() {
        when(presidentDao.queryAllPresidents()).thenReturn(new ArrayList<President>());
        assertEquals(new ArrayList<President>(), presidentBusinessService.findAllPresidents());
    }

    @Test
    void testFindAllPresidents_DaoThrowsException() {
        when(presidentDao.queryAllPresidents()).thenThrow(new RuntimeException("Mock DAO Exception"));
        assertThrows(PresidentDatabaseException.class, () -> {
            presidentBusinessService.findAllPresidents();
        });
    }

    @Test
    void testFindPresidentById() {
        int id = 1;
        when(presidentDao.queryPresidentById(id)).thenReturn(president1);
        President president = presidentBusinessService.findPresidentById(id);
        assertEquals(president1, president);
    }

    @Test
    void testFindPresidentById_ZeroId() {
        int id = 0;
        when(presidentDao.queryPresidentById(id)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> {
            presidentBusinessService.findPresidentById(id);
        });
    }

    @Test
    void testFindPresidentById_NegativeId() {
        int id = -1;
        when(presidentDao.queryPresidentById(id)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> {
            presidentBusinessService.findPresidentById(id);
        });
    }

}
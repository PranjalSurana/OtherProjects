package com.roifmr.presidents.restcontroller;

import com.roifmr.presidents.integration.PresidentDao;
import com.roifmr.presidents.business.President;
import com.roifmr.presidents.business.service.PresidentDatabaseException;
import com.roifmr.presidents.business.service.PresidentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class PresidentsControllerPojoUnitTest {

    @Mock
    private PresidentDao presidentDao;

    @InjectMocks
    private PresidentServiceImpl presidentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllPresidents() {
        List<President> allPresidents = Arrays.asList(
                new President(1,"f1","l1",1789,1797,"img1.jpg","bio1"),
                new President(2, "f2", "l2", 1989, 2000, "img2.jpg", "bio2"),
                new President(3, "f3", "l3", 1889, 1930, "img3.jpg", "bio3")
        );
        when(presidentDao.getAllPresidents()).thenReturn(allPresidents);
        assertEquals(allPresidents, presidentService.findAllPresidents());
    }

    @Test
    void testFindAllPresidents_DaoReturnsEmptyList() {
        when(presidentDao.getAllPresidents()).thenReturn(new ArrayList<President>());
        assertEquals(new ArrayList<President>(), presidentService.findAllPresidents());
    }

    @Test
    void testFindAllPresidents_DaoThrowsException() {
        // Configure the mock DAO to throw an exception when getAllPresidents is called
        when(presidentDao.getAllPresidents())
                .thenThrow(new RuntimeException("mock DAO exception"));

        assertThrows(PresidentDatabaseException.class, () -> {
            presidentService.findAllPresidents();
        });
    }

    @Test
    void testFindPresidentById() {
        int id = 1;
        President firstPresident = new President(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(presidentDao.getPresident(id))
                .thenReturn(firstPresident);

        President president = presidentService.findPresidentById(id);

        assertEquals(firstPresident, president);
    }

    @Test
    void testFindPresidentById_IdNotExists() {
        when(presidentDao.getPresident(1000))
                .thenThrow(new RuntimeException("mock DAO exception"));

        assertThrows(PresidentDatabaseException.class, () -> {
            presidentService.findPresidentById(1);
        });
    }

    @Test
    void testAddPresident() {
        President president = new President(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(presidentDao.insertPresident(president))
                .thenReturn(1);

        int rowsInserted = presidentService.addPresident(president);

        assertEquals(1, rowsInserted);
    }

    @Test
    void testAddPresident_DaoThrowsWarehouseDatabaseException() {
        int id = 1;
        President president = new President(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(presidentDao.insertPresident(president))
                .thenThrow(new PresidentDatabaseException("mock DAO exception"));

        assertThrows(PresidentDatabaseException.class, () -> {
            presidentService.addPresident(president);
        });
    }

    @Test
    void testAddPresident_DaoThrowsDuplicateKeyException() {
        int id = 1;
        President president = new President(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(presidentDao.insertPresident(president))
                .thenThrow(new DuplicateKeyException("mock DAO exception"));

        assertThrows(DuplicateKeyException.class, () -> {
            presidentService.addPresident(president);
        });
    }

    @Test
    void testAddPresident_NullPresident() {
        assertThrows(IllegalArgumentException.class, () -> {
            presidentService.addPresident(null);
        });
    }

    @Test
    void testAddPresident_PresidentMissingBio() {
        President president = new President(1,"f1","l1",1789,1797,"img1.jpg","");

        assertThrows(IllegalArgumentException.class, () -> {
            presidentService.addPresident(president);
        });
    }

    @Test
    void testUpdatePresident() {
        President originalPresident = new President(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(presidentDao.updatePresident(originalPresident))
                .thenReturn(1);

        int count = presidentService.modifyPresident(originalPresident);

        assertEquals(1, count);
    }

    @Test
    void testUpdatePresident_IdNotExists() {
        President president = new President(10, "f", "l", 1973, 1988, "i.img", "b1");
        when(presidentDao.updatePresident(president))
                .thenThrow(new RuntimeException("mock DAO exception"));

        assertThrows(PresidentDatabaseException.class, () -> {
            presidentService.modifyPresident(president);
        });
    }

    @Test
    void testDeletePresident() {
        int id = 1;
        when(presidentDao.deletePresident(id))
                .thenReturn(1);

        int count = presidentService.removePresident(id);

        assertEquals(1, count);
    }

    @Test
    void testDeletePresident_IdNotExists() {
        when(presidentDao.deletePresident(10))
                .thenThrow(new RuntimeException("mock DAO exception"));

        assertThrows(PresidentDatabaseException.class, () -> {
            presidentService.removePresident(10);
        });
    }

}
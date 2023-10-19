package com.roifmr.presidents.business.service;

import com.roifmr.presidents.business.President;
import com.roifmr.presidents.integration.PresidentDao;
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

public class PresidentBusinessServicePojoUnitTest {

    @Mock
    private PresidentDao mockDao;

    // TODO: note the definition of the business service instance that is being tested
    //       (no code changes required)
    @InjectMocks
    private PresidentServiceImpl service;

    // TODO: note the call to openMocks() in the @BeforeEach method.
    //       This calls the WarehouseBusinessServiceImpl constructor and
    //       injects a mock DAO into the business service object.
    //       (no code changes required)
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // TODO: write a test case that verifies the business service's findAllPresidents method
    //       behaves as expected when the DAO returns a list with several presidents
    // HINT: see slide 3-18
    @Test
    void testFindAllPresidents() {
        List<President> allPresidents = Arrays.asList(
                new President(1,"f1","l1",1789,1797,"img1.jpg","bio1"),
                new President(2, "f2", "l2", 1989, 2000, "img2.jpg", "bio2"),
                new President(3, "f3", "l3", 1889, 1930, "img3.jpg", "bio3")
        );
        when(mockDao.getAllPresidents()).thenReturn(allPresidents);
        assertEquals(allPresidents, service.findAllPresidents());
    }

    // TODO: write a test case that verifies the business service's findAllPresidents 
    //       method behaves as expected when the DAO returns an empty list
    @Test
    void testFindAllPresidents_DaoReturnsEmptyList() {
        when(mockDao.getAllPresidents()).thenReturn(new ArrayList<President>());
        assertEquals(new ArrayList<President>(), service.findAllPresidents());
    }

    // TODO: note the following test case, which verifies the business service's findAllPresidents
    //       method behaves as expected when the DAO throws a RuntimeException
    //       (no code changes required)
    @Test
    void testFindAllPresidents_DaoThrowsException() {
        // Configure the mock DAO to throw an exception when getAllPresidents is called
        when(mockDao.getAllPresidents())
                .thenThrow(new RuntimeException("mock DAO exception"));

        assertThrows(PresidentDatabaseException.class, () -> {
            service.findAllPresidents();
        });
    }

    // the remaining test cases test different scenarios for the business service's methods

    @Test
    void testFindPresidentById() {
        int id = 1;
        President firstPresident = new President(1,"f1","l1",1789,1797,"img1.jpg","bio1");
        
        when(mockDao.getPresident(id))
                .thenReturn(firstPresident);

        President president = service.findPresidentById(id);

        // verify the President 
        assertEquals(firstPresident, president);
    }

    @Test
    void testAddPresident() {
        President president = new President(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(mockDao.insertPresident(president))
                .thenReturn(1);

        int rowsInserted = service.addPresident(president);

        assertEquals(1, rowsInserted);
    }

    @Test
    void testAddPresident_DaoThrowsPresidentDatabaseException() {
        int id = 1;
        President president = new President(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(mockDao.insertPresident(president))
                .thenThrow(new PresidentDatabaseException("mock DAO exception"));

        assertThrows(PresidentDatabaseException.class, () -> {
            service.addPresident(president);
        });
    }

    @Test
    void testAddPresident_DaoThrowsDuplicateKeyException() {
        int id = 1;
        President president = new President(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(mockDao.insertPresident(president))
                .thenThrow(new DuplicateKeyException("mock DAO exception"));

        assertThrows(DuplicateKeyException.class, () -> {
            service.addPresident(president);
        });
    }

    @Test
    void testAddPresident_NullPresident() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.addPresident(null);
        });
    }

    @Test
    void testAddPresident_PresidentMissingBio() {
        President president = new President(1,"f1","l1",1789,1797,"img1.jpg","");

        assertThrows(IllegalArgumentException.class, () -> {
            service.addPresident(president);
        });
    }

    @Test
    void testUpdatePresident() {
        President originalPresident = new President(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(mockDao.updatePresident(originalPresident))
                .thenReturn(1);

        int count = service.modifyPresident(originalPresident);

        assertEquals(1, count);
    }

    @Test
    void testDeletePresident() {
        int id = 1;
        when(mockDao.deletePresident(id))
                .thenReturn(1);

        int count = service.removePresident(id);

        assertEquals(1, count);
    }

}
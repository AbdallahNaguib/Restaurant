package com.orange.restaurant.service;

import com.orange.restaurant.model.DinnerTable;
import com.orange.restaurant.model.Reservation;
import com.orange.restaurant.model.dto.DinnerTableRequestDTO;
import com.orange.restaurant.model.dto.TimeRange;
import com.orange.restaurant.repository.TableRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TableServiceTest {
    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private TableService tableService;
    List<DinnerTable> mockDinnerTablesList;
    private long currentTableId;

    @BeforeEach
    void init() {
        mockDinnerTablesList = new ArrayList<>();
        currentTableId = 1;
    }

    private void mockSavingInRepo() {
        when(tableRepository.save(any()))
                .thenAnswer((Answer<DinnerTable>) par -> {
                    DinnerTable table = par.getArgument(0, DinnerTable.class);
                    table.setId(currentTableId++);
                    mockDinnerTablesList.add(table);
                    return table;
                });
    }

    private void save2MockedItems() {
        tableService.save(DinnerTableRequestDTO.builder()
                .name("t1")
                .maxPersons((short) 10)
                .build());
        tableService.save(DinnerTableRequestDTO.builder()
                .name("t2")
                .maxPersons((short) 20)
                .build());
    }

    @Test
    public void testSavingTables() {
        mockSavingInRepo();
        save2MockedItems();
        Assertions.assertEquals(mockDinnerTablesList.size(), 2);
    }

    private void mockFindAllDinnerTables() {
        DinnerTable t1 = DinnerTable.builder()
                .id(1L)
                .tableName("t1")
                .maxPersons((short) 10)
                .reservations(new ArrayList<>())
                .build();
        List<Reservation> t1Res = new ArrayList<>();
        t1Res.add(Reservation.builder()
                .table(t1)
                .startTime(1L)
                .endTime(10L)
                .build());
        t1Res.add(Reservation.builder()
                .table(t1)
                .startTime(20L)
                .endTime(30L)
                .build());
        t1.setReservations(t1Res);
        DinnerTable t2= DinnerTable.builder()
                .id(2L)
                .tableName("t2")
                .maxPersons((short) 10)
                .reservations(new ArrayList<>())
                .build();
        List<Reservation> t2Res = new ArrayList<>();
        t2Res.add(Reservation.builder()
                .startTime(8L)
                .endTime(13L)
                .build());
        t2Res.add(Reservation.builder()
                .startTime(17L)
                .endTime(25L)
                .build());
        t2.setReservations(t2Res);

        DinnerTable t3 = DinnerTable.builder()
                .id(3L)
                .tableName("t3")
                .maxPersons((short) 10)
                .reservations(new ArrayList<>())
                .build();
        List<Reservation> t3Res = new ArrayList<>();
        t3Res.add(Reservation.builder()
                .startTime(9L)
                .endTime(17L)
                .build());
        t3Res.add(Reservation.builder()
                .startTime(22L)
                .endTime(29L)
                .build());
        t3.setReservations(t3Res);

        List<DinnerTable> tables = new ArrayList<>();
        tables.add(t1);
        tables.add(t2);
        tables.add(t3);

        when(tableRepository.findAll())
                .thenReturn(tables);
    }

    //ToDo we prefer naming our tests with given_when_then
    //givenTestPrecondtion_whenTestedAction_thenExpectedResult
    //givenAvailableTablesExists_whenGetAvailableTables_thenOnlyAvailableTablesRetrieved
    //Preferably don't test your service layer if there's no computation, instead do an offline integration test
    //Checkout MVC tests with testcontainers for database mocking
    @Test
    public void testGetAvailableTables() {
        mockFindAllDinnerTables();
        //ToDo a testcase should be readable, mockfindAll doesn't show what is the precondition
        //How about you create a method to add a table with reservations and call it multiple times?
        // [1:10,20:30][8:13,17:25][9:17,22:29]

        //ToDo you are exeucting the same tests multiple times, to check when available tables exist and no available tables
        //a test case should have only one reason to fail, create a separate case to validate no available tests
        // Also checkout data driven tests to execute the same test case with multiple values
        Assertions.assertEquals(tableService.getAvailableTables(TimeRange.builder()
                .startTime(15L)
                .endTime(16L)
                .build()).size(),2);

        Assertions.assertEquals(tableService.getAvailableTables(TimeRange.builder()
                .startTime(5L)
                .endTime(13L)
                .build()).size(),0);
        Assertions.assertEquals(tableService.getAvailableTables(TimeRange.builder()
                .startTime(30L)
                .endTime(40L)
                .build()).size(),3);

        Assertions.assertEquals(tableService.getAvailableTables(TimeRange.builder()
                .startTime(1L)
                .endTime(5L)
                .build()).size(),2);
    }

    @Test
    public void testCheckIfAvailable(){
        //ToDo same as above
        DinnerTable t3 = DinnerTable.builder()
                .tableName("t3")
                .maxPersons((short) 10)
                .reservations(new ArrayList<>())
                .build();
        List<Reservation> t3Res = new ArrayList<>();
        t3Res.add(Reservation.builder()
                .startTime(1L)
                .endTime(15L)
                .build());
        t3Res.add(Reservation.builder()
                .startTime(25L)
                .endTime(33L)
                .build());
        t3.setReservations(t3Res);
        Assertions.assertFalse(tableService.checkIfAvailable(t3, TimeRange.builder()
                .startTime(13L)
                .endTime(17L)
                .build()));
        Assertions.assertTrue(tableService.checkIfAvailable(t3, TimeRange.builder()
                .startTime(15L)
                .endTime(20L)
                .build()));
        Assertions.assertTrue(tableService.checkIfAvailable(t3, TimeRange.builder()
                .startTime(20L)
                .endTime(24L)
                .build()));
        Assertions.assertTrue(tableService.checkIfAvailable(t3, TimeRange.builder()
                .startTime(34L)
                .endTime(50L)
                .build()));
    }
}

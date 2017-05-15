import models.Company;
import models.Computer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import persistence.DAOFactory;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import persistence.daos.CompanyDao;
import persistence.daos.ComputerDao;
import services.CompanyService;
import services.ComputerService;
import services.dtos.CompanyDTOServiceImpl;

@RunWith(SpringRunner.class)
public class ComputerServiceTest {

    private ComputerService computerService;

    @MockBean
    DAOFactory daoFactory;
    @MockBean
    ComputerDao computerDao;
    @MockBean
    CompanyDao companyDao;

    @Before
    public void setupMock() {
        computerService = new ComputerService(computerDao, companyDao, daoFactory);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAll() {
        computerService = new ComputerService(computerDao, companyDao, daoFactory);
        MockitoAnnotations.initMocks(this);

        //CREATE 2 COMPUTERS
        Company company1 = new Company( 1L, "Apple Inc.");
        Company company2 = new Company(2L, "Thinking Machines");

        Computer computer1 = new
                Computer.ComputerBuilder()
                .id(1L)
                .name("MacBook Pro 15.4 inch")
                .company(company1)
                .build();

        Computer computer11 = new
                Computer.ComputerBuilder()
                .id(11L)
                .name("Apple II Plus")
                .company(company2)
                .build();

        //Computer 1 :
        //Write expected behaviors
        when(computerDao.create(computer1)).thenReturn(1L);
        //Act
        computerDao.create(computer1);
        //Check
        verify(computerDao).create(computer1);

        //computer 2:
        /* when(computerDao.create(computer11)).thenReturn(11L);
        computerDao.create(computer1);
        verify(computerDao).create(computer1); */

        //Get computers
        Computer computer = mock(Computer.class);
        List<Computer> expectedComputerList = new ArrayList<>();
        expectedComputerList.add(computer1);
       /* expectedComputerList.add(computer11); */

        when(computerDao.getAll()).thenReturn(expectedComputerList);
        expectedComputerList = computerDao.getAll();
        verify(computerDao).getAll();

        //SET DAOS IN COMPUTER SERVICE
        computerService.setCompanyDao(companyDao);
        computerService.setComputerDao(computerDao);
        computerService.setDaoFactory(daoFactory);

        //CHECK
        List<Computer> actualComputerList = computerService.getAll();
        assertEquals(expectedComputerList.get(0), actualComputerList.get(0));
        /* assertEquals(expectedComputerList.get(10), actualComputerList.get(10)); */
    }

}



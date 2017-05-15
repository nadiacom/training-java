import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import persistence.DAOFactory;
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import persistence.daos.CompanyDao;
import persistence.daos.ComputerDao;
import services.CompanyService;
import services.dtos.CompanyDTOServiceImpl;

/**
 * Created by ebiz on 17/03/17.
 */
@RunWith(SpringRunner.class)
public class CompanyServiceTest {

    private CompanyService companyService;

    @MockBean
    DAOFactory daoFactory;
    @MockBean
    ComputerDao computerDao;
    @MockBean
    CompanyDao companyDao;

    @Before
     public void setupMock() {
        companyService = new CompanyService(companyDao, computerDao, daoFactory);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAll(){
        companyService = new CompanyService(companyDao, computerDao, daoFactory);
        MockitoAnnotations.initMocks(this);

        //Create 2 companies
        //Write expected behaviors

    }
/*
    @Resource
    protected CompanyService companyService;
    protected Company company, company10;

    @Before
    public void setUp() {
        companyService = new CompanyService();
        company = new Company( (long) 1, "Apple Inc.");
        company10 = new Company( (long) 10, "Digital Equipment Corporation");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetAllCompany() throws Exception {
        List<Company> companies = companyService.getAll();
        assertEquals(company.toString(), companies.get(0).toString());
        assertEquals(company10.toString(), companies.get(9).toString());
    }

    @Test
    public void findBiId() throws Exception {
       Company company = companyService.findById(1L);

    }*/

}



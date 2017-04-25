import models.Company;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.CompanyService;
import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ebiz on 17/03/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class CompanyServiceTest {

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

    }

}



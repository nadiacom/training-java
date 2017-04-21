import models.Company;
import models.Computer;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.ComputerService;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ebiz on 17/03/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class ComputerServiceTest {

    @Resource
    protected ComputerService computerService;
    protected Computer computer, computer11;
    protected Company company, company2, companyNull;

    @Before
    public void setUp() {
        computerService = new ComputerService();
        company = new Company( (long) 1, "Apple Inc.");
        company2 = new Company((long)2, "Thinking Machines");
        companyNull = null;

        computer = new
                Computer.ComputerBuilder()
                .id(1L)
                .name("MacBook Pro 15.4 inch")
                .company(company)
                .build();

        computer11 = new
                Computer.ComputerBuilder()
                .id(11L)
                .name("Apple II Plus")
                .company(companyNull)
                .build();

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetAllComputer() throws Exception {
        List<Computer> computers = computerService.getAll();
        assertEquals(computer.toString(), computers.get(0).toString());
        assertEquals(computer11.toString(), computers.get(10).toString());
    }

    @Test
    public void testGetComputerByPage() throws Exception {
        int page = 2;
        int nbComputerByPage = 10;
        List<Computer> computers = computerService.getByPage(page, nbComputerByPage);
        assertEquals(computer11.toString(), computers.get(0).toString());
    }




}



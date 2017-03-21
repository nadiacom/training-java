import models.Company;
import models.Computer;
import persistence.daos.ComputerDaoImpl;
import cli.ComputerCli;
import services.validators.Input;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;

/**
 * Created by ebiz on 17/03/17.
 */
public class ComputerTest {

    private static Input inputCliService;

    private static final String COMPUTER_NAME = "src/test";
    private static final LocalDate INTRODUCED = inputCliService.getLocalDate("2017-05-05");
    private static final LocalDate DISCONTINUED = inputCliService.getLocalDate("2017-08-05");
    private static final int COMPANY_ID = 1;
    /**
     *
     * @param args input
     */
    public static void main(String[] args){
        //mocks creation
        ComputerDaoImpl computerDaoImpl = mock(ComputerDaoImpl.class);
        ComputerCli computerService = mock(ComputerCli.class);

        //models instantiation
        Company c = new Company(1L, "Apple Inc.");
        Computer computer = new
                Computer.ComputerBuilder()
                .name(COMPUTER_NAME)
                .introduced(INTRODUCED)
                .discontinued(DISCONTINUED)
                .company(c)
                .build();

        //Create computer
        computerService.createComputer(COMPUTER_NAME, INTRODUCED, DISCONTINUED, COMPANY_ID);

        computerDaoImpl.findById(4L);
    }



}



import persistence.daos.CompanyDaoImpl;
import cli.CompanyCli;

import static org.mockito.Mockito.mock;

/**
 * Created by ebiz on 17/03/17.
 */
public class CompanylTest {

    public static void main(String[] args){
        //mocks creation
        CompanyDaoImpl companyDaoImpl = mock(CompanyDaoImpl.class);
        CompanyCli companyService = mock(CompanyCli.class);

        companyDaoImpl.findById(4L);
    }



}



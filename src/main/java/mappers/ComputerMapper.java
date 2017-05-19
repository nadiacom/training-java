package mappers;

import models.Company;
import models.Computer;
import models.dtos.ComputerDTO;
import org.springframework.stereotype.Service;
import utils.DatePatternUtils;

@Service("CompanyMapper")
public class ComputerMapper implements GenericMapper<Computer, ComputerDTO> {

    public CompanyMapper companyMapper = new CompanyMapper();

    /**
     * Default constructor.
     */
    public ComputerMapper() {
    }

    static DatePatternUtils datePatternUtils = new DatePatternUtils();

    /**
     * Map computerDTO to computer.
     *
     * @param computerDTO computerDTO.
     * @return mapped computer from computerDTO.
     */
    public Computer to(ComputerDTO computerDTO) {
        Computer computer = new
                Computer.ComputerBuilder()
                .id(computerDTO.getId() != null ? Long.valueOf(computerDTO.getId()) : null)
                .name(computerDTO.getName())
                .introduced(computerDTO.getIntroduced() != null ? datePatternUtils.getLocalDateTime(computerDTO.getIntroduced()) : null)
                .discontinued(computerDTO.getDiscontinued() != null ? datePatternUtils.getLocalDateTime(computerDTO.getDiscontinued()) : null)
                //Get companyDTO and map it to company
                .company(new Company(computerDTO.getCompanyId(), computerDTO.getCompanyName()))
                .build();
        return computer;
    }

    /**
     * Map computer to companyDTO.
     *
     * @param computer computer.
     * @return mapped companyDTO from computer.
     */
    public ComputerDTO from(Computer computer) {
        ComputerDTO computerDTO = new
                ComputerDTO.ComputerDTOBuilder()
                .id(computer.getId())
                .name(computer.getName())
                .introduced(computer.getDiscontinued() != null ? computer.getIntroduced().toString() : "")
                .discontinued(computer.getDiscontinued() != null ? computer.getDiscontinued().toString() : "")
                //Get companyDTO and map it to company
                .companyId(computer.getCompany() != null ? computer.getCompany().getId() : null)
                .companyName(computer.getCompany() != null ? computer.getCompany().getName() : null)
                .build();
        return computerDTO;
    }

    public void setCompanyMapper(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    public CompanyMapper getCompanyMapper() {
        return companyMapper;
    }
}

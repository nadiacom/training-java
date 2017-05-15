package mappers;

import models.Computer;
import models.dtos.ComputerDTO;
import services.validators.inputs.Input;

public class ComputerMapper implements Mapper<Computer, ComputerDTO> {

    public CompanyMapper companyMapper = new CompanyMapper();

    /**
     * Default constructor.
     */
    public ComputerMapper() {
    }

    static Input input = new Input();

    /**
     * Map computerDTO to computer.
     *
     * @param computerDTO computerDTO.
     * @return mapped computer from computerDTO.
     */
    public Computer to(ComputerDTO computerDTO) {
        Computer computer = new
                Computer.ComputerBuilder()
                .id(Long.valueOf(computerDTO.getId()))
                .name(computerDTO.getName())
                .introduced(computerDTO.getIntroduced() != null ? input.getLocalDate(computerDTO.getIntroduced()) : null)
                .discontinued(computerDTO.getDiscontinued() != null ? input.getLocalDate(computerDTO.getDiscontinued()) : null)
                //Get companyDTO and map it to company
                .company(companyMapper.to(computerDTO.getCompanyDTO()))
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
                .company(companyMapper.from(computer.getCompany()))
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

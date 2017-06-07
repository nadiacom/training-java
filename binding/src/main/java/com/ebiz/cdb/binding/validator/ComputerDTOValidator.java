package com.ebiz.cdb.binding.validator;

import com.ebiz.cdb.binding.dto.ComputerDTO;
import com.ebiz.cdb.binding.utils.DatePatternUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class ComputerDTOValidator implements Validator {

    DatePatternUtils dateUtils = new DatePatternUtils();

    /**
     * This Validator validates *just* Computer instances.
     *
     * @param computerDTO computerDTO instace to check.
     * @return is a computerDTo instance or not
     */
    @Override
    public boolean supports(Class<?> computerDTO) {
        return ComputerDTO.class.equals(computerDTO);
    }

    /**
     * Validate computerDTO pattern.
     *
     * @param obj input obj (check if instance of computer before)
     * @param e   error
     */
    public void validate(Object obj, Errors e) {
        ValidationUtils.rejectIfEmpty(e, "name", "error.computer.name");
        ComputerDTO computerDTO = (ComputerDTO) obj;
        //TODO : check company exists

        String introduced = computerDTO.getIntroduced();
        String discontinued = computerDTO.getDiscontinued();

        if (!discontinued.isEmpty() && introduced.isEmpty()) {
            e.rejectValue("discontinued", "error.computer.introduced.missing");
        }
        if (!introduced.isEmpty()) {
            if (!isPatternValid(introduced)) {
                e.rejectValue("introduced", "error.computer.introduced.pattern");
            }
        } else if (!discontinued.isEmpty()) {
            if (!isPatternValid(discontinued)) {
                e.rejectValue("discontinued", "error.computer.discontinued.pattern");
            }
        } else if (!introduced.isEmpty() && !discontinued.isEmpty()) {
            if (e.hasFieldErrors("error.computer.introduced.pattern") && e.hasFieldErrors("error.computer.discontinued.pattern")) {
                if (!isValidIntervalDate(introduced, discontinued)) {
                    e.rejectValue("discontinued", "error.computer.discontinued.before");
                }
            }
        }
    }

    /**
     * Introduced date validator.
     *
     * @param input input date.
     * @return boolean : date valid or not.
     */
    public boolean isPatternValid(String input) {
        return dateUtils.isPatternValid(input);
    }

    /**
     * @param introduced   introduced date.
     * @param discontinued discontinued date.
     * @return boolean : interval valid or not.
     */
    public boolean isValidIntervalDate(String introduced, String discontinued) {
        LocalDateTime intro = dateUtils.getLocalDateTime(introduced);
        LocalDateTime discon = dateUtils.getLocalDateTime(discontinued);
        return discon.isAfter(intro);
    }
}
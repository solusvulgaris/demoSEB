package com.ak.springbootdemo.seb.web;

import com.ak.springbootdemo.seb.constants.SourceType;
import com.ak.springbootdemo.seb.exceptions.SubsidiaryControllerException;
import com.ak.springbootdemo.seb.service.SubsidiaryService;
import com.ak.springbootdemo.seb.util.JSONSubsidiary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Subsidiary Controller
 */
@Controller
@RequestMapping("/subsidiaries")
public class SubsidiaryController {
    private static final Logger logger = Logger.getLogger(SubsidiaryController.class.getName());

    private final SubsidiaryService subsidiaryService;

    public SubsidiaryController(SubsidiaryService subsidiaryService) {
        this.subsidiaryService = subsidiaryService;
    }

    /**
     * Get & Bind Subsidiaries List to the model
     *
     * @param model input model
     * @return response view name
     */
    @GetMapping
    public String getSubsidiaries(@RequestParam(value = "type", required = false) String typeString, Model model)
            throws SubsidiaryControllerException {
        if (typeString == null) {
            model.addAttribute("subsidiariesList", this.subsidiaryService.getSubsidiaries());
        } else {
            final Optional<SourceType> sourceTypeOptional = SourceType.getSourceType(typeString);
            if (sourceTypeOptional.isEmpty()) {
                throw new SubsidiaryControllerException(String.format("Unknown source type value: '%s'.", typeString));
            } else {
                switch (sourceTypeOptional.get()) {
                    case JSON:
                        List<JSONSubsidiary> jsonSubsidiaryList = this.subsidiaryService.saveSubsidiariesFromJSONFile();
                        model.addAttribute("subsidiariesList", jsonSubsidiaryList);
                        break;
                    case XML: {
                        //TODO: implement reading from xml
                        break;
                    }
                    default:
                        break;
                }
            }
        }
        return "subsidiariesView";
    }

    /**
     * Exception handler if SubsidiaryControllerException is thrown in this Controller
     *
     * @param ex exception
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({SubsidiaryControllerException.class,})
    public String return400(SubsidiaryControllerException ex) {
        logger.log(Level.WARNING, ex.getMessage());
        return ex.getMessage();
    }
}

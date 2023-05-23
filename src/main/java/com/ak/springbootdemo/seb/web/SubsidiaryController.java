package com.ak.springbootdemo.seb.web;

import com.ak.springbootdemo.seb.service.SubsidiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Subsidiary Controller
 */
@Controller
@RequestMapping("/subsidiaries")
public class SubsidiaryController {
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
    public String getSubsidiaries(Model model) {
        model.addAttribute("subsidiariesList", this.subsidiaryService.getSubsidiaries());
        return "subsidiariesView";
    }

}

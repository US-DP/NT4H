package org.springframework.samples.nt4h.achievement;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.constants.MessageConstants;
import org.springframework.samples.nt4h.constants.PageConstants;
import org.springframework.samples.nt4h.constants.ViewConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/achievements")
public class AchievementController {

    // Services ---------------------------------------------------------------
    private final AchievementService achievementService;

    // Constructor ------------------------------------------------------------
    @Autowired
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }


    // Show all achievements ---------------------------------------------------
    @GetMapping
    public String showAllAchievements() {
        return ViewConstants.LIST_ACHIEVEMENTS;
    }

    // Create achievement ------------------------------------------------------
    @GetMapping("/new")
    public String createAchievement(ModelMap model) {
        model.put("achievement", new Achievement());
        return ViewConstants.CREATE_OR_UPDATE_FORM_ACHIEVEMENTS;
    }

    @PostMapping("/new")
    public String saveAchievement(@Valid Achievement achievement, BindingResult br, ModelMap model) {
        if(br.hasErrors())
            return createAchievement(model);
        achievementService.saveAchievement(achievement);
        MessageConstants.addMessage(model, MessageConstants.CREATE_SUCCESS_ACHIEVEMENT);
        return showAllAchievements();
    }

    // Edit achievement --------------------------------------------------------
    @GetMapping("/{achievementId}/edit")
    public String editAchievement(@PathVariable int achievementId, ModelMap model) {
        Achievement achievement = achievementService.getAchievementById(achievementId);
        model.addAttribute(achievement);
        return ViewConstants.CREATE_OR_UPDATE_FORM_ACHIEVEMENTS;
    }

    @PostMapping("/{achievementId}/edit")
    public String saveAchievement(@PathVariable int achievementId,@Valid Achievement actualAchievement, BindingResult br, ModelMap model) {
        if(br.hasErrors())
            return editAchievement(achievementId, model);
        Achievement achievementToBeUpdated = achievementService.getAchievementById(achievementId);
        BeanUtils.copyProperties(actualAchievement, achievementToBeUpdated,"id");
        achievementService.saveAchievement(achievementToBeUpdated);
        MessageConstants.addMessage(model, MessageConstants.UPDATE_SUCCESS_ACHIEVEMENT);
        return PageConstants.LIST_ACHIEVEMENTS;
    }

    @GetMapping(value = "{achievementId}/delete")
    public String deleteAchievement(@PathVariable int achievementId, ModelMap model) {
        achievementService.deleteAchievementById(achievementId);
        MessageConstants.addMessage(model, MessageConstants.DELETE_SUCCESS_ACHIEVEMENT);
        return PageConstants.LIST_ACHIEVEMENTS;
    }
}

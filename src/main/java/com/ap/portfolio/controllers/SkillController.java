package com.ap.portfolio.controllers;

import com.ap.portfolio.dtos.ExperienceDTO;
import com.ap.portfolio.dtos.SkillDTO;
import com.ap.portfolio.models.Experience;
import com.ap.portfolio.models.Skill;
import com.ap.portfolio.security.roles.IUser;
import com.ap.portfolio.security.services.UserService;
import com.ap.portfolio.services.SkillService;
import com.ap.portfolio.utilities.Message;
import com.ap.portfolio.utilities.Utils;
import jdk.jshell.execution.Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill")
@CrossOrigin(origins = "https://portfoliotestfront.web.app")
public class SkillController {
    @Autowired
    private SkillService skillService;
    @Autowired
    private UserService userService;

    @GetMapping("/active-skill")
    public ResponseEntity<List<Skill>> getActive() {
        return new ResponseEntity<>(this.skillService.findByActiveTrue(), HttpStatus.OK);
    }

    @GetMapping("/get-skill/{id}")
    public ResponseEntity<Skill> getSkill(@PathVariable int id) {
        return new ResponseEntity<>(this.skillService.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping("active-skill-by-id/{id}")
    public ResponseEntity<List<Skill>> activeSkillById(@PathVariable int id){
        List<Skill> skillList = this.skillService.findActiveSkillByUserId(id);
        return new ResponseEntity<>(skillList, HttpStatus.OK);
    }

    @PostMapping("/create-skill")
    public ResponseEntity<?> createSkill(@RequestBody SkillDTO skillDTO) {
        if (StringUtils.isBlank(skillDTO.getSkillName())) {
            return new ResponseEntity<>(new Message("Missing Skill Name"), HttpStatus.BAD_REQUEST);
        }
        if (Double.isNaN(skillDTO.getPercentage())) {
            return new ResponseEntity<>(new Message("Percentage must be a number"), HttpStatus.BAD_REQUEST);
        }
        if (skillDTO.getPercentage() < 0) {
            return new ResponseEntity<>(new Message("Percentage must be a number between 0 and 100"), HttpStatus.BAD_REQUEST);
        }
        if (skillDTO.getPercentage() > 100) {
            return new ResponseEntity<>(new Message("Percentage must be a number between 0 and 100"), HttpStatus.BAD_REQUEST);
        }
        if (this.skillService.existsBySkillName(skillDTO.getSkillName())
                && this.skillService.findByUserAndSkillName(Utils.getCurrentUser(this.userService).getWebUser(),skillDTO.getSkillName())) {
            return new ResponseEntity<>(new Message("Skill already exists"), HttpStatus.BAD_REQUEST);
        }

        Skill skill = new Skill(skillDTO.getSkillName(), skillDTO.getPercentage(), skillDTO.getImgURL(),Utils.getCurrentUser(this.userService).getWebUser());

        skill.setUser(Utils.getCurrentUser(this.userService).getWebUser());

        this.skillService.save(skill);

        return new ResponseEntity<>(new Message("Skill Added"), HttpStatus.CREATED);
    }

    @PatchMapping("/update-skill/{id}")
    public ResponseEntity<?> modifySkill(@PathVariable int id, @RequestBody SkillDTO skillDTO) {
        try {
            double percentage = Double.parseDouble(String.valueOf(skillDTO.getPercentage()));
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(new Message("Percentage must be a number"), HttpStatus.BAD_REQUEST);
        }
        if (!this.skillService.existsById(id)) {
            return new ResponseEntity<>(new Message("Skill not found"), HttpStatus.BAD_REQUEST);
        }

        StringBuilder sb = new StringBuilder().append("Modified: ");

        Skill skill = this.skillService.findById(id).get();

        if (StringUtils.isNotBlank(skillDTO.getSkillName())) {
            skill.setSkillName(skill.getSkillName());
            sb.append("name, ");
        }

        if (skillDTO.getPercentage() < 0 || skillDTO.getPercentage() > 100) {
            return new ResponseEntity<>(new Message("Percentage must be a number between 0 and 100"), HttpStatus.BAD_REQUEST);
        } else if (this.skillService.existsBySkillName(skillDTO.getSkillName()) && this.skillService.findBySkillName(skillDTO.getSkillName()).get().getId() != id) {
            return new ResponseEntity<>(new Message("Skill already exists"), HttpStatus.BAD_REQUEST);
        } else {
            skill.setPercentage(skillDTO.getPercentage());
            sb.append("percentage, ");
        }

        if(StringUtils.isNotBlank(skillDTO.getImgURL())){
            skill.setImgURL(skillDTO.getImgURL());
            sb.append("image, ");
        }


        sb.delete(sb.length() - 2, sb.length());
        sb.append(".");

        if(sb.toString().equals("Modified.")){
            sb.replace(0, sb.length(), "Nothing was modified.");
        }

        this.skillService.save(skill);


        return new ResponseEntity<>(new Message(sb.toString()), HttpStatus.OK);
    }

    @PatchMapping("/disable-skill/{id}")
    public ResponseEntity<?> disableSkill(@PathVariable int id) {
        if (!this.skillService.existsById(id)) {
            return new ResponseEntity<>(new Message("Skill not found"), HttpStatus.BAD_REQUEST);
        }

        Skill skill = this.skillService.findById(id).get();

        skill.setActive(false);

        this.skillService.save(skill);

        String sb = "Experience " +
                skill.getSkillName() +
                " disabled";

        return new ResponseEntity<>(new Message(sb), HttpStatus.OK);
    }
}

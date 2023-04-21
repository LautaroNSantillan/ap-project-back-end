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
    public ResponseEntity<?> createExp(@RequestBody SkillDTO skillDTO) {
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

        Skill skill = new Skill(skillDTO.getSkillName(), skillDTO.getPercentage(), Utils.getCurrentUser(this.userService).getWebUser());

        skill.setUser(Utils.getCurrentUser(this.userService).getWebUser());

        this.skillService.save(skill);

        return new ResponseEntity<>(new Message("Skill Added"), HttpStatus.CREATED);
    }

    @PatchMapping("/update-skill/{id}")
    public ResponseEntity<?> modifyExp(@PathVariable int id, @RequestBody SkillDTO skillDTO) {
        if (!this.skillService.existsById(id)) {
            return new ResponseEntity<>(new Message("Skill not found"), HttpStatus.BAD_REQUEST);
        }
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
        if (this.skillService.existsBySkillName(skillDTO.getSkillName()) && this.skillService.findBySkillName(skillDTO.getSkillName()).get().getId() != id) {
            return new ResponseEntity<>(new Message("Skill already exists"), HttpStatus.BAD_REQUEST);
        }

        String oldSkillName = this.skillService.findById(id).get().getSkillName();

        Skill skill = this.skillService.findById(id).get();

        skill.setSkillName(skillDTO.getSkillName());
        skill.setPercentage(skillDTO.getPercentage());

        this.skillService.save(skill);

        String sb = "Skill " +
                oldSkillName +
                " was modified";

        return new ResponseEntity<>(new Message(sb), HttpStatus.OK);
    }

    @PatchMapping("/disable-skill/{id}")
    public ResponseEntity<?> disableExp(@PathVariable int id) {
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

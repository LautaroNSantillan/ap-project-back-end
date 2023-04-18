package com.ap.portfolio.controllers;

import com.ap.portfolio.dtos.EducationDTO;
import com.ap.portfolio.models.Education;
import com.ap.portfolio.security.roles.IUser;
import com.ap.portfolio.security.services.UserService;
import com.ap.portfolio.services.EducationService;
import com.ap.portfolio.utilities.Message;
import com.ap.portfolio.utilities.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/edu")
public class EducationController {
    @Autowired
    private EducationService educationService;
    @Autowired
    private UserService userService;
    @GetMapping("active-edu")
    public ResponseEntity<List<Education>> activeEdu(){
        List<Education> educationList = this.educationService.activeEduList();
        return new ResponseEntity<>(educationList, HttpStatus.OK);
    }
    @GetMapping("get-edu/{id}")
    public ResponseEntity<?> getEdu(@PathVariable int id){
        if(!this.educationService.existsById(id)){
            return new ResponseEntity<>(new Message("Education not found"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.educationService.findById(id).get(), HttpStatus.OK);
    }
    @PatchMapping("disable-edu/{id}")
    public ResponseEntity<?> disable(@PathVariable int id){
        if(!this.educationService.existsById(id)){
            return new ResponseEntity<>(new Message("Education not found"), HttpStatus.BAD_REQUEST);
        }
        Education education = this.educationService.findById(id).get();

        this.educationService.disable(education);

        return new ResponseEntity<>(new Message("Disabled " + education.getEduName()), HttpStatus.OK);
    }

    @PostMapping("create-edu")
    public ResponseEntity<?> create(@RequestBody EducationDTO educationDTO){
        if(StringUtils.isBlank(educationDTO.getEduName())){
            return new ResponseEntity<>(new Message("Missing name"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(educationDTO.getEduDescription())){
            return new ResponseEntity<>(new Message("Missing description"), HttpStatus.BAD_REQUEST);
        }
        if(this.educationService.existsByName(educationDTO.getEduName())){
            return new ResponseEntity<>(new Message("Education already exists"), HttpStatus.BAD_REQUEST);
        }

        Education education = new Education(educationDTO.getEduName(), educationDTO.getEduDescription(), Utils.getCurrentUser(this.userService));

        this.educationService.save(education);

        return new ResponseEntity<>(new Message("Saved "+ education.getEduName()), HttpStatus.CREATED);
    }

    @PatchMapping("update-edu/{id}")
    public ResponseEntity<?> updateEdu(@PathVariable int id, @RequestBody EducationDTO educationDTO) {
        if(StringUtils.isBlank(educationDTO.getEduName())){
            return new ResponseEntity<>(new Message("Missing name"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(educationDTO.getEduDescription())){
            return new ResponseEntity<>(new Message("Missing description"), HttpStatus.BAD_REQUEST);
        }
        if(!this.educationService.existsById(id)){
            return new ResponseEntity<>(new Message("Education not found"), HttpStatus.BAD_REQUEST);
        }
        if(this.educationService.existsByName(educationDTO.getEduName()) && this.educationService.findById(id).get().getId()!=id){
            return new ResponseEntity<>(new Message("Education already exists"), HttpStatus.BAD_REQUEST);
        }

        Education education = this.educationService.findById(id).get();

        String oldEduName = educationDTO.getEduName();

        education.setEduName(educationDTO.getEduName());
        education.setEduDescription(educationDTO.getEduDescription());

        this.educationService.save(education);

        return new ResponseEntity<>(new Message("Education "+ oldEduName+" was modified"), HttpStatus.OK);
    }
}

package com.ap.portfolio.controllers;

import com.ap.portfolio.dtos.EducationDTO;
import com.ap.portfolio.models.Education;
import com.ap.portfolio.models.WebUser;
import com.ap.portfolio.security.roles.IUser;
import com.ap.portfolio.security.services.UserService;
import com.ap.portfolio.services.EducationService;
import com.ap.portfolio.services.WebUserService;
import com.ap.portfolio.utilities.Message;
import com.ap.portfolio.utilities.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://portfoliotestfront.web.app")
@RequestMapping("/edu")
public class EducationController {
    @Autowired
    private EducationService educationService;
    @Autowired
    private UserService userService;
    @Autowired
    private WebUserService webUserService;
    @GetMapping("active-edu")
    public ResponseEntity<List<Education>> activeEdu(){
        List<Education> educationList = this.educationService.activeEduList();
        return new ResponseEntity<>(educationList, HttpStatus.OK);
    }
    @GetMapping("active-edu-by-id/{id}")
    public ResponseEntity<List<Education>> activeEduById(@PathVariable int id){
        List<Education> educationList = this.educationService.findActiveEducationByUserId(id);
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

        Education education = new Education(educationDTO.getEduName(), educationDTO.getEduDescription(), Utils.getCurrentUser(this.userService).getWebUser(), educationDTO.getImgURL());

        this.educationService.save(education);

        return new ResponseEntity<>(new Message("Saved "+ education.getEduName()), HttpStatus.CREATED);
    }

    @PatchMapping("update-edu/{id}")
    public ResponseEntity<?> updateEdu(@PathVariable int id, @RequestBody EducationDTO educationDTO) {
        if(!this.educationService.existsById(id)){
            return new ResponseEntity<>(new Message("Education not found"), HttpStatus.BAD_REQUEST);
        }
        if(this.educationService.existsByName(educationDTO.getEduName()) && this.educationService.findById(id).get().getId()!=id){
            return new ResponseEntity<>(new Message("Education already exists"), HttpStatus.BAD_REQUEST);
        }

        StringBuilder sb = new StringBuilder().append("Modified: ");

        Education education = this.educationService.findById(id).get();

        if(StringUtils.isNotBlank(educationDTO.getEduName())){
            education.setEduName(educationDTO.getEduName());
            sb.append("name, ");
        }
        if(StringUtils.isNotBlank(educationDTO.getEduDescription())){
            education.setEduDescription(educationDTO.getEduDescription());
            sb.append("description, ");
        }
        if(StringUtils.isNotBlank(educationDTO.getImgURL())){
            education.setImgURL(educationDTO.getImgURL());
            sb.append("image, ");
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append(".");

        if(sb.toString().equals("Modified.")){
            sb.replace(0, sb.length(), "Nothing was modified.");
        }

        String oldEduName = educationDTO.getEduName();

        this.educationService.save(education);

        return new ResponseEntity<>(new Message(sb.toString()), HttpStatus.OK);
    }
}

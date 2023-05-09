package com.ap.portfolio.controllers;

import com.ap.portfolio.dtos.ExperienceDTO;
import com.ap.portfolio.models.Education;
import com.ap.portfolio.models.Experience;
import com.ap.portfolio.models.WebUser;
import com.ap.portfolio.security.roles.IUser;
import com.ap.portfolio.security.services.UserService;
import com.ap.portfolio.services.ExperienceService;
import com.ap.portfolio.utilities.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exp")
@CrossOrigin(origins = "https://portfoliotestfront.web.app")
public class ExperienceController {
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private UserService userService;
    @GetMapping("/all-exp")
    public ResponseEntity<List<Experience>> getAll(){
        return new ResponseEntity<>(this.experienceService.allExp(), HttpStatus.OK) ;
    }
    @GetMapping("/active-exp")
    public ResponseEntity<List<Experience>> getActive(){
        return new ResponseEntity<>(this.experienceService.activeExps(), HttpStatus.OK) ;
    }
    @GetMapping("/get-exp/{id}")
    public ResponseEntity<Experience> getExp(@PathVariable int id){
        return new ResponseEntity<>(this.experienceService.findById(id).get(), HttpStatus.OK) ;
    }
    @GetMapping("active-exp-by-id/{id}")
    public ResponseEntity<List<Experience>> activeExpById(@PathVariable int id){
        List<Experience> expList = this.experienceService.findActiveEducationByUserId(id);
        return new ResponseEntity<>(expList, HttpStatus.OK);
    }
    @PostMapping("/create-exp")
    public ResponseEntity<?> createExp(@RequestBody ExperienceDTO experienceDTO){
        if(StringUtils.isBlank(experienceDTO.getExpName())){
            return new ResponseEntity<>(new Message("Missing Experience Name"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(experienceDTO.getExpDescription())){
            return new ResponseEntity<>(new Message("Missing Experience Description"), HttpStatus.BAD_REQUEST);
        }
        if(this.experienceService.existstByName(experienceDTO.getExpName())){
            return new ResponseEntity<>(new Message("Experience already exists"), HttpStatus.BAD_REQUEST);
        }

        Experience exp = new Experience(experienceDTO.getExpName(), experienceDTO.getExpDescription(), experienceDTO.getImgURL());

        //hacer servicio
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        WebUser user = this.userService.getByUsername(authUserName).get().getWebUser();

        exp.setUser(user);

        this.experienceService.save(exp);

        return new ResponseEntity<>(new Message("Experience Added"), HttpStatus.CREATED);
    }

    @PatchMapping("/update-exp/{id}")
    public ResponseEntity<?> modifyExp (@PathVariable int id, @RequestBody ExperienceDTO experienceDTO){
        if(!this.experienceService.existstById(id)){
            return new ResponseEntity<>(new Message("Experience not found"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(experienceDTO.getExpName())){
            return new ResponseEntity<>(new Message("Missing Experience Name"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(experienceDTO.getExpDescription())){
            return new ResponseEntity<>(new Message("Missing Experience Description"), HttpStatus.BAD_REQUEST);
        }
        if(this.experienceService.existstByName(experienceDTO.getExpName())
                && this.experienceService.findByName(experienceDTO.getExpName()).get().getId() ==id){
            return new ResponseEntity<>(new Message("Experience already exists"), HttpStatus.BAD_REQUEST);
        }

        String oldExpName = this.experienceService.findById(id).get().getExpName();

        Experience exp = this.experienceService.findById(id).get();

        exp.setExpName(experienceDTO.getExpName());
        exp.setExpDescription(experienceDTO.getExpDescription());

        this.experienceService.save(exp);

        StringBuilder sb = new StringBuilder();
        sb.append("Experience ");
        sb.append(oldExpName);
        sb.append(" was modified");

        return new ResponseEntity<>(new Message(sb.toString()), HttpStatus.OK);
    }

    @PatchMapping("/disable-exp/{id}")
    public ResponseEntity<?> disableExp(@PathVariable int id){
        if(!this.experienceService.existstById(id)){
            return new ResponseEntity<>(new Message("Experience not found"), HttpStatus.BAD_REQUEST);
        }

        Experience exp = this.experienceService.findById(id).get();

        exp.setActive(false);

        this.experienceService.save(exp);

        StringBuilder sb = new StringBuilder();
        sb.append("Experience ");
        sb.append(exp.getExpName());
        sb.append(" disabled");

        return new ResponseEntity<>(new Message(sb.toString()), HttpStatus.OK);
    }
}

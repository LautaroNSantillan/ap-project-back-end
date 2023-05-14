package com.ap.portfolio.utilities;

import com.ap.portfolio.dtos.SkillDTO;
import com.ap.portfolio.security.roles.MainUser;
import com.ap.portfolio.security.roles.IUser;
import com.ap.portfolio.security.services.UserService;
import com.ap.portfolio.services.SkillService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {
    public static Date createDate(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0); // set hour to 0
        cal.set(Calendar.MINUTE, 0);      // set minute to 0
        cal.set(Calendar.SECOND, 0);      // set second to 0
        cal.set(Calendar.MILLISECOND, 0); // set millisecond to 0
        return cal.getTime();
    }

    public static MainUser buildMainUser(IUser user){
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());

        return new MainUser(user.getName(), user.getUsername(), user.getPwd(), authorities);
    }

    public static IUser getCurrentUser(UserService userService){
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getByUsername(authUserName).get();
    }

    public static boolean validateSkillDTO(SkillDTO skillDTO, long id, SkillService skillService) {
        if (skillDTO.getPercentage() < 0 || skillDTO.getPercentage() > 100) {
            return false;
        }
        if (skillService.existsBySkillName(skillDTO.getSkillName()) && skillService.findBySkillName(skillDTO.getSkillName()).get().getId() != id) {
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

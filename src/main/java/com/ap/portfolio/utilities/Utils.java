package com.ap.portfolio.utilities;

import com.ap.portfolio.security.roles.MainUser;
import com.ap.portfolio.security.roles.IUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

        return new MainUser(user.getName(), user.getUsername(), user.getEmail(), user.getPwd(), authorities);
    }
}

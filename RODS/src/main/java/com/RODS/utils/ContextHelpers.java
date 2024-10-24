package com.RODS.utils;



import com.RODS.entity.Logins;
import com.RODS.security.UserDetailsImpl;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Locale;

public class ContextHelpers {
    /**
     * Gets from LocaleContextHolder current language
     * @return String "en" or "ua"
     */
    public static boolean isLocaleEnglish() {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH);
    }

    /**
     * Gets from SecurityContextHolder current authorized user
     * @return Login entity
     */
    public static Logins getAuthorizedLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        return userDetails.getLogin();
    }

}

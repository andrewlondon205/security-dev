package com.luv2code.springsecurity.demo.controller;

import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.UserService;
import com.luv2code.springsecurity.demo.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;


    private Logger logger = Logger.getLogger(getClass().getName());

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {
        theModel.addAttribute("crmUser",new CrmUser());
        theModel.addAttribute("roles",roles);
        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, BindingResult theBindingResult, Model theModel) {

        String userName = theCrmUser.getUserName();
        logger.info("Processing registration form for: " + userName);

        // form validation
        if (theBindingResult.hasErrors()) {

            theModel.addAttribute("crmUser",new CrmUser());
            theModel.addAttribute("roles",roles);

            return "registration-form";
        }

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList();
        authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));

        // if the user selected role other than employee
        // then add that one too (multiple roles)
        String formRole = theCrmUser.getFormRole();
        if (!formRole.equals("ROLE_EMPLOYEE")) {
            authorities.add(new SimpleGrantedAuthority(formRole));
        }

            // check the database if user already exists // save user in the database
        User existing = userService.findByUserName(userName);
        if(existing!=null) {
            theModel.addAttribute("crmUser", new CrmUser());
            theModel.addAttribute("registrationError", "User name already exists.");
            logger.warning("User name already exists.");
            return "registration-form";
        }

        userService.save(theCrmUser);
        logger.info("Successfully created user: " + userName);
        return "registration-confirmation";
    }

    private Map<String,String> roles;
    @PostConstruct
    protected void loadRoles() {
        roles = new LinkedHashMap<String,String>();
        roles.put("ROLE_EMPLOYEE", "Employee");
        roles.put("ROLE_MANAGER", "Manager");
        roles.put("ROLE_ADMIN", "Admin");
    }




}

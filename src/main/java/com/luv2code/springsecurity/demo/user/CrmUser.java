package com.luv2code.springsecurity.demo.user;

import com.luv2code.springsecurity.demo.validation.FieldMatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldMatch.List(@FieldMatch(first="password",second="matchingPassword",message = "The password fields must match"))
public class CrmUser {

    @NotNull(message = "is required")
    @Size(min=1,message = "is required")
    private String userName;

    @NotNull(message = "is required")
    @Size(min=1,message = "is required")
    private String password;

    @NotNull(message = "is required")
    @Size(min=1, message = "is required")
    private String matchingPassword;

    @NotNull(message = "is required")
    @Size(min=1,message = "is required")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 1,message = "is required")
    private String lastName;

    @NotNull(message = "is required")
    @Size(min = 1,message = "is required")
    private String email;

    private String formRole;



}

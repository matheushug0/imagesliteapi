package br.com.mh.imageliteapi.application.users;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String email;
}

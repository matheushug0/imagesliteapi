package br.com.mh.imageliteapi.application.users;

import lombok.Data;

@Data
public class CredentialsDTO {
    private String password;
    private String email;
}

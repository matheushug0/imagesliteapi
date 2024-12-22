package br.com.mh.imageliteapi.domain.service;

import br.com.mh.imageliteapi.domain.AccessToken;
import br.com.mh.imageliteapi.domain.entity.User;

public interface UserService {
    User findByEmail(String email);
    User save(User user);
    AccessToken authenticate(String email, String password);
}

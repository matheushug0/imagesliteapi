package br.com.mh.imageliteapi.application.users;

import br.com.mh.imageliteapi.application.jwt.JwtService;
import br.com.mh.imageliteapi.domain.AccessToken;
import br.com.mh.imageliteapi.domain.entity.User;
import br.com.mh.imageliteapi.domain.exception.DuplicatedTupleException;
import br.com.mh.imageliteapi.domain.service.UserService;
import br.com.mh.imageliteapi.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        User possibleUser = findByEmail(user.getEmail());
        if(possibleUser != null) {
            throw new DuplicatedTupleException("Já existe um usuário cadastrado com esse email");
        }
        encodePassword(user);
        return userRepository.save(user);
    }

    @Override
    public AccessToken authenticate(String email, String password) {
        User user = findByEmail(email);
        if(user == null) {
            return null;
        }
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if(matches) {
            return jwtService.getAccessToken(user);
        }
        return null;
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}

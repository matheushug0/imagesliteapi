package br.com.mh.imageliteapi.application.users;

import br.com.mh.imageliteapi.domain.AccessToken;
import br.com.mh.imageliteapi.domain.entity.User;
import br.com.mh.imageliteapi.domain.exception.DuplicatedTupleException;
import br.com.mh.imageliteapi.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity save(@RequestBody UserDTO userDTO) {
        try {
            User user = userMapper.mapToUser(userDTO);
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DuplicatedTupleException e) {
            Map<String, String> jsonResult = Map.of("Error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonResult);
        }
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody CredentialsDTO CredentialsDTO) {
        AccessToken token = userService.authenticate(CredentialsDTO.getEmail(), CredentialsDTO.getPassword());
        if (token == null) {
            Map<String, String> jsonResult = Map.of("Error", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonResult);
        }
        return ResponseEntity.ok(token);
    }
}

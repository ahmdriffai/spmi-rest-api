package id.ac.fksp.spmi.repository;

import id.ac.fksp.spmi.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void successfindByUsername() {
        //given
        String username = "rifai";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setEmail("rifai@mail.id");
        user.setPassword("rahasia");
        user.setRoles(null);
        User save = userRepository.save(user);

        //when
        Boolean exists = userRepository.existsByUsername(username);
        // then
        Assertions.assertTrue(exists);

    }

    @Test
    @Disabled
    void findByEmail() {
    }

    @Test
    @Disabled
    void existsByUsername() {
    }

    @Test
    @Disabled
    void existsByEmail() {
    }
}
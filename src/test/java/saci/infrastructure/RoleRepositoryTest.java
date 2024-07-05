package saci.infrastructure;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RoleRepositoryTest {

    //    @Autowired RoleRepository roleRepository;
    //
    //    @Test
    //    void shouldPersistRole() {
    //        Role expectedRole = Role.builder().name("role").build();
    //
    //        roleRepository.save(expectedRole);
    //
    //        Optional<Role> role = roleRepository.findById(expectedRole.getId());
    //        assertThat(role.isPresent()).isTrue();
    //        assertThat(role.get()).isEqualTo(expectedRole);
    //    }
    //
    //    @Test
    //    void shouldGetAllRoles() {
    //        Role expectedRole1 = Role.builder().name("role1").build();
    //        Role expectedRole2 = Role.builder().name("role2").build();
    //        List<Role> expectedRoles = List.of(expectedRole1, expectedRole2);
    //        roleRepository.saveAll(expectedRoles);
    //
    //        List<Role> roles = roleRepository.findAll();
    //
    //        assertThat(roles.isEmpty()).isFalse();
    //        assertThat(roles).containsAll(expectedRoles);
    //    }
}

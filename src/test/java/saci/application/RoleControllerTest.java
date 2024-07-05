package saci.application;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    //    @Autowired private MockMvc mockMvc;
    //    @MockBean private RoleService roleService;
    //
    //       @Test
    //       void shouldCreateRole() throws Exception {
    //           Role expectedRole = Role.builder().name("role").build();
    //          given(roleService.createRole(expectedRole)).willReturn(expectedRole);
    //          String expectedJsonRole = new ObjectMapper().writeValueAsString(expectedRole);
    //        mockMvc.perform(
    //                post("/api/roles")
    //                        .contentType(MediaType.APPLICATION_JSON)
    //                        .content(expectedJsonRole))
    //                .andExpect(status().isCreated())
    //                .andExpect(content().json(expectedJsonRole));
    //      }
    //
    //    @Test
    //    void shouldListAllRoles() throws Exception {
    //        Role expectedRole1 = Role.builder().name("role1").build();
    //        Role expectedRole2 = Role.builder().name("role2").build();
    //        List<Role> expectedRoles = List.of(expectedRole1, expectedRole2);
    //        given(roleService.getRoles()).willReturn(expectedRoles);
    //
    //        mockMvc.perform(get("/api/roles"))
    //                .andExpect(status().isOk())
    //                .andExpect(content().json(new
    // ObjectMapper().writeValueAsString(expectedRoles)));
}

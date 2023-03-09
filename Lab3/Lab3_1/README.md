# Lab 3_1

## Review Questions

### a) Identify a couple of examples on the use of AssertJ expressive methods chaining.

#### AssertJ expressive methods chaining in this project:

- A_EmployeeRepositoryTest.java

  ```java
  assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
  ```

  

- B_EmployeeService_UnitTest.java

  ```java
  assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());
  ```

  

- D_EmployeeRestControllerIT.java

  ```java
  assertThat(found).extracting(Employee::getName).containsOnly("bob");
  ```

  

- E_EmployeeRestControllerTemplateIT.java

  ```java
  assertThat(found).extracting(Employee::getName).containsOnly("bob");
  assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");
  ```

  

### b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).

- B_EmployeeService_UnitTest.java

  ```java
      @InjectMocks
      private EmployeeServiceImpl employeeService;
  
      @BeforeEach
      public void setUp() {
  
          //these expectations provide an alternative to the use of the repository
          Employee john = new Employee("john", "john@deti.com");
          john.setId(111L);
  
          Employee bob = new Employee("bob", "bob@deti.com");
          Employee alex = new Employee("alex", "alex@deti.com");
  
          List<Employee> allEmployees = Arrays.asList(john, bob, alex);
  
          Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
          Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
          Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
          Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
          Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
          Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
      }
  ```

- C_EmployeeController_WithMockServiceTest.java

  ```java
      @MockBean
      private EmployeeService service;
      @Test
      void whenPostEmployee_thenCreateEmployee( ) throws Exception {
          Employee alex = new Employee("alex", "alex@deti.com");
  
          when( service.save(Mockito.any()) ).thenReturn( alex);
  
          mvc.perform(
          post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(alex)))
                  .andExpect(status().isCreated())
                  .andExpect(jsonPath("$.name", is("alex")));
  
          verify(service, times(1)).save(Mockito.any());
  
      }
  
      @Test
          void givenManyEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {
              Employee alex = new Employee("alex", "alex@deti.com");
              Employee john = new Employee("john", "john@deti.com");
              Employee bob = new Employee("bob", "bob@deti.com");
  
              List<Employee> allEmployees = Arrays.asList(alex, john, bob);
  
              when( service.getAllEmployees()).thenReturn(allEmployees);
  
              mvc.perform(
                      get("/api/employees").contentType(MediaType.APPLICATION_JSON))
                      .andExpect(status().isOk())
                      .andExpect(jsonPath("$", hasSize(3)))
                      .andExpect(jsonPath("$[0].name", is(alex.getName())))
                      .andExpect(jsonPath("$[1].name", is(john.getName())))
                      .andExpect(jsonPath("$[2].name", is(bob.getName())));
  
              verify(service, times(1)).getAllEmployees();
          }
  ```

- 
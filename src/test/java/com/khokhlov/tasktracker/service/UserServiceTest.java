package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.exception.UserAlreadyExistsException;
import com.khokhlov.tasktracker.mapper.UserMapper;
import com.khokhlov.tasktracker.model.command.UserCommand;
import com.khokhlov.tasktracker.model.dto.UserDTO;
import com.khokhlov.tasktracker.provider.SessionProvider;
import com.khokhlov.tasktracker.provider.TaskTrackerSessionProvider;
import com.khokhlov.tasktracker.repository.UserRepository;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {


    private static SessionFactory sessionFactory;
    private static UserService userService;
    private static UserRepository userRepository;
    private static Liquibase liquibase;
    private static Connection connection;

    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");


    @BeforeAll
    @SneakyThrows
    public static void beforeAll() {
        postgresContainer.start();
        connection = DriverManager.getConnection(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword());

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update("");
    }

    @AfterAll
    @SneakyThrows
    public static void afterAll() {
        if (liquibase != null) {
            liquibase.close();
        }
        if (connection != null) {
            connection.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }

        postgresContainer.stop();
    }

    @BeforeEach
    void setUp() {
        SessionProvider sessionProvider = new TaskTrackerSessionProvider(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );
        sessionFactory = sessionProvider.getSessionFactory();

        userRepository = new UserRepository();
        UserMapper userMapper = UserMapper.INSTANCE;
        userService = new UserService(sessionFactory, userRepository, userMapper);
    }

    @AfterEach
    void tearDown() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        userRepository.deleteAll(session);
        transaction.commit();
    }

    @Test
    @Order(1)
    void should_SaveUser_When_UserIsValid() {
        UserCommand userCommand = new UserCommand("testName", "testLastName", "validUsername", "123");

        UserDTO savedUser = userService.save(userCommand);
        UserDTO expectedUser = new UserDTO(1L, "testName", "testLastName");

        assertNotNull(savedUser);
        assertEquals(expectedUser, userService.findAll().getFirst());
    }

    @Test
    @Order(2)
    void should_ThrowException_When_UsernameAlreadyExists() {
        UserCommand userCommand = new UserCommand("testName", "testLastName", "validUsername", "123");
        userService.save(userCommand);

        UserCommand duplicateUserCommand = new UserCommand("testName", "testLastName", "validUsername", "123");

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.save(duplicateUserCommand);
        });
    }
}
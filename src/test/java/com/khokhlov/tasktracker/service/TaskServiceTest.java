package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.mapper.TaskMapper;
import com.khokhlov.tasktracker.mapper.UserMapper;
import com.khokhlov.tasktracker.model.command.LoginCommand;
import com.khokhlov.tasktracker.model.command.TaskCommand;
import com.khokhlov.tasktracker.model.command.UserCommand;
import com.khokhlov.tasktracker.model.dto.TaskDTO;
import com.khokhlov.tasktracker.model.entity.Tag;
import com.khokhlov.tasktracker.model.entity.TaskStatus;
import com.khokhlov.tasktracker.model.entity.User;
import com.khokhlov.tasktracker.provider.SessionProvider;
import com.khokhlov.tasktracker.provider.TaskTrackerSessionProvider;
import com.khokhlov.tasktracker.repository.TaskRepository;
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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskServiceTest {


    private static SessionFactory sessionFactory;
    private static TaskService taskService;
    private static TaskRepository taskRepository;
    private static UserRepository userRepository;
    private static Liquibase liquibase;
    private static Connection connection;
    private static User user;

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

        taskRepository = new TaskRepository();
        userRepository = new UserRepository();
        TaskMapper taskMapper = TaskMapper.INSTANCE;
        taskService = new TaskService(sessionFactory, taskRepository, taskMapper);

        UserService userService = new UserService(sessionFactory, userRepository, UserMapper.INSTANCE);
        userService.save(new UserCommand("testName", "testLastName", "validUsername", "123"));
        user = userService.authenticate(new LoginCommand("validUsername", "123"));
    }

    @AfterEach
    void tearDown() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        taskRepository.deleteAll(session);
        userRepository.deleteAll(session);
        transaction.commit();
    }

    @Test
    @Order(1)
    void should_SaveTask_When_ProvideValidData() {
        HashSet<Tag> tags = new HashSet<>();
        tags.add(new Tag("testTag"));
        TaskCommand taskCommand = new TaskCommand(null, "testTitle", "testDescription", LocalDate.now(), TaskStatus.NOT_STARTED, tags);
        TaskDTO savedTask = taskService.saveTask(taskCommand, user);

        TaskDTO expectedTask = new TaskDTO(1L, "testTitle", "testDescription", LocalDate.now(), TaskStatus.NOT_STARTED, tags);
        assertNotNull(savedTask);
        assertEquals(expectedTask, savedTask);
    }

    @Test
    @Order(2)
    void should_ReturnTask_When_ProvideExistingUser() {
        HashSet<Tag> tags = new HashSet<>();
        tags.add(new Tag("testTag"));
        TaskCommand taskCommand = new TaskCommand(null, "testTitle", "testDescription", LocalDate.now(), TaskStatus.NOT_STARTED, tags);

        TaskDTO savedTask = taskService.saveTask(taskCommand, user);
        List<TaskDTO> taskList = taskService.findAllTasksByUsername(user.getUsername());

        assertEquals(1, taskList.size());
        assertEquals(savedTask, taskList.getFirst());
    }

    @Test
    @Order(3)
    void should_UpdateTask_When_ProvideValidData() {
        HashSet<Tag> tags = new HashSet<>();
        tags.add(new Tag("testTag"));
        TaskCommand taskCommand = new TaskCommand(null, "testTitle", "testDescription", LocalDate.now(), TaskStatus.NOT_STARTED, tags);
        TaskCommand taskUpdate = new TaskCommand(3L, "testAnotherTitle", "testAnotherDescription", LocalDate.now(), TaskStatus.DONE, tags);

        taskService.saveTask(taskCommand, user);
        TaskDTO updatedTask = taskService.update(taskUpdate);

        TaskDTO expected = new TaskDTO(3L, updatedTask.getTitle(), updatedTask.getDescription(), LocalDate.now(), TaskStatus.DONE, tags);

        assertEquals(expected, updatedTask);
    }

    @Test
    @Order(4)
    void should_ReturnTask_When_ProvidedValidTaskId() {
        HashSet<Tag> tags = new HashSet<>();
        tags.add(new Tag("testTag"));
        TaskCommand taskCommand = new TaskCommand(null, "testTitle", "testDescription", LocalDate.now(), TaskStatus.NOT_STARTED, tags);

        TaskDTO expectedTask = taskService.saveTask(taskCommand, user);
        TaskDTO actualTask = taskService.getTaskById(4L);

        assertEquals(expectedTask, actualTask);
    }
}
package com.khokhlov.tasktracker.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @SequenceGenerator(name = "todo_seq",
            sequenceName = "todos_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_seq")
    private Long id;

    private String title;

    private String username;

    private String description;

    @Column(name = "target_date")
    private LocalDate targetDate;

    private boolean status;


    public Todo(long id, String title, String username, String description, LocalDate targetDate, boolean isDone) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.description = description;
        this.targetDate = targetDate;
        this.status = isDone;
    }

    public Todo(String title, String username, String description, LocalDate targetDate, boolean isDone) {
        this.title = title;
        this.username = username;
        this.description = description;
        this.targetDate = targetDate;
        this.status = isDone;
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Todo todo = (Todo) o;
        return getId() != null && Objects.equals(getId(), todo.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
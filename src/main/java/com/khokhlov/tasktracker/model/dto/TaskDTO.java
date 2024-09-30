package com.khokhlov.tasktracker.model.dto;

import com.khokhlov.tasktracker.model.entity.Comment;
import com.khokhlov.tasktracker.model.entity.Tag;
import com.khokhlov.tasktracker.model.entity.Task;
import com.khokhlov.tasktracker.model.entity.TaskStatus;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link Task}
 */
@Value
public class TaskDTO implements Serializable, DTO {
    Long id;
    String title;
    String description;
    LocalDate targetDate;
    TaskStatus status;
    Set<Tag> tags;
    Set<Comment> comments;
}
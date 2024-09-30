package com.khokhlov.tasktracker.model.command;

import com.khokhlov.tasktracker.model.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TaskCommand implements Command {
    private Long id;
    private String title;
    private String description;
    private LocalDate targetDate;
    private TaskStatus status;
}
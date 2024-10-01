package com.khokhlov.tasktracker.model.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khokhlov.tasktracker.model.entity.Tag;
import com.khokhlov.tasktracker.model.entity.TaskStatus;
import com.khokhlov.tasktracker.model.utils.TagDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

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

    @JsonDeserialize(contentUsing = TagDeserializer.class)
    private Set<Tag> tags;
}
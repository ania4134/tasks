package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskDto)) return false;

        TaskDto taskDto = (TaskDto) o;

        if (!getId().equals(taskDto.getId())) return false;
        if (getTitle() != null ? !getTitle().equals(taskDto.getTitle()) : taskDto.getTitle() != null) return false;
        return getContent() != null ? getContent().equals(taskDto.getContent()) : taskDto.getContent() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        return result;
    }
}

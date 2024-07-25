package ru.practicum.ewm.comments.model.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UpdateCommentDto {
    public Integer eventId;
    @NotNull
    @NotBlank
    @Length(min = 3, max = 2000)
    public String text;
}

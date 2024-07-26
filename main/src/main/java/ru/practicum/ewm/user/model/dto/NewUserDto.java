package ru.practicum.ewm.user.model.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class NewUserDto {
    @Email
    @NotEmpty
    @Length(min = 6, max = 254)
    private String email;
    @NotEmpty
    @NotBlank
    @Length(min = 2, max = 250)
    private String name;
}

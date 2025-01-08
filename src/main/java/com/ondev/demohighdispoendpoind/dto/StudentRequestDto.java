package com.ondev.demohighdispoendpoind.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonSerialize
@JsonDeserialize
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StudentRequestDto {
    @NotNull(message = "L'ID ne peut pas être nul")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "L'ID doit être au format UUID")
    private String id;

    @NotBlank(message = "Le chemin ne peut pas être vide")
    private String path;

    @NotEmpty(message = "Le statut ne peut pas être vide")
    private String status;
}
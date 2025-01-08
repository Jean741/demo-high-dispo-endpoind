package com.ondev.demohighdispoendpoind.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author MJean-Claude
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonSerialize
@JsonDeserialize
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class EtudiantDto {
    private String nom;
    private String prenom;
    private int age;
}

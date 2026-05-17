package com.programacion.distribuida.authors.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private Long id; // Usamos Long porque tu Entidad usa Long
    private String name;
}

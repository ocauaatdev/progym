package com.progym.progym.modules.aluno.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthAlunoResponseDTO {
    
    private String acess_token;
    private Long expires_in;
}

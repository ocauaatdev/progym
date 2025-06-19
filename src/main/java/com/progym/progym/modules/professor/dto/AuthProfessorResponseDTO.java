package com.progym.progym.modules.professor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthProfessorResponseDTO {
    
    private String acess_token;
    private Long expires_in;
}

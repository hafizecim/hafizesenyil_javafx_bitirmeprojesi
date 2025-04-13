package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto;

import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.utils.ERole;
import lombok.*;
import java.time.LocalDateTime;

import java.util.Optional;

// Lombok
@Getter
@Setter
//@AllArgsConstructor // Parametreli Constructor
@NoArgsConstructor  // Parametresiz Constructor
@ToString
@Builder

// UserDTO
public class UserDTO {
    // Field
    private Integer id;
    private String username;
    private String password;
    private String email;
    private ERole role;

    // ✅ Yeni alanlar:
    private LocalDateTime joinDate;    // Üyelik tarihi
    private LocalDateTime lastLogin;   // Son giriş tarihi

    // Parametresiz Constructor

    // Parametreli Constructor

    public UserDTO(Integer id, String username, String password, String email, ERole role,
                   LocalDateTime joinDate, LocalDateTime lastLogin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.joinDate = joinDate;
        this.lastLogin = lastLogin;
    }


    // Getter And Setter
    // Method

    /*
    public static void main(String[] args) {
        UserDTO userDTO= UserDTO.builder()
                .id(0)
                .username("username")
                .email("hafizesenyil@gmail.com")
                .password("root")
                .build();

        System.out.println(userDTO);
    }
    */


} //end Class

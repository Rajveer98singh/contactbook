package com.book.address.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateContactRequest {

    @NotBlank
    private String id;

    private String name;
    private String phone;

    @Email
    private String email;
}

package com.devfrank.hotelmanager.users.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserCriteria {

    private String role;

    private String email;
}
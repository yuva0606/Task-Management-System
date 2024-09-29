package com.yuva.app.utils;

import com.yuva.app.entities.Role;

public record RegisterResponse(Integer userId, String username, String email, Role role) {

}

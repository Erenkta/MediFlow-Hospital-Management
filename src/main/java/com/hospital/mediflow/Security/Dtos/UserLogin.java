package com.hospital.mediflow.Security.Dtos;

import jakarta.validation.constraints.NotEmpty;

public record UserLogin(@NotEmpty String username, @NotEmpty String password) {
}

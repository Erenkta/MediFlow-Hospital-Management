package com.hospital.mediflow.Common.Providers.Abstracts;

import com.hospital.mediflow.Security.Dtos.Entity.User;

public interface CurrentUserProvider {
    User get();
}

package com.hospital.mediflow.Security.UserDetails.Repository;

import com.hospital.mediflow.Security.Dtos.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
}

package com.revature.repositories;

import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthDAO extends JpaRepository<User, Integer> {
    User findByUserNameAndPassword(String userName, String password);
}

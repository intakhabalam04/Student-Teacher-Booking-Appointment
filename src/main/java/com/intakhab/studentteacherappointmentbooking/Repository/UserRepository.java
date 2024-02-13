package com.intakhab.studentteacherappointmentbooking.Repository;

import com.intakhab.studentteacherappointmentbooking.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByTokenNo(String tokenNo );
    User findByEmailOrMobileOrUsername(String email,String mobile,String username);
}

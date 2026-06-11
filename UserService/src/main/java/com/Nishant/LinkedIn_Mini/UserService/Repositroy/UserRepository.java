package com.Nishant.LinkedIn_Mini.UserService.Repositroy;

import com.Nishant.LinkedIn_Mini.UserService.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    //practice a case  in which  , write the sql query to fetch the data from the
    //database rather than using the conventional name to generate the sql query
    
}

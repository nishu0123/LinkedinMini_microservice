package com.Nishant.LinkedIn_Mini.UserService.Service;

import com.Nishant.LinkedIn_Mini.UserService.Exception.DuplicateUserNameException;
import com.Nishant.LinkedIn_Mini.UserService.FeignClient.UserFeign;
import com.nishant.linkedinmini.common.contracts.ApiResponse;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.PersonDto;
import feign.FeignException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class ConnectionNodeService {

    @Autowired
    private final UserFeign userFeign;

    public void createNode(Long userId , PersonDto personDto)
    {
        try
        {
            ResponseEntity<ApiResponse<PersonDto>> response =  userFeign.addUserNode(userId , personDto);
            //if execution reached to the below logs it means that node has been created
            log.info("node created for username : " + personDto.getUserName());
        }
        catch (FeignException ex){

            //check the status code
            if (ex.status() == 409) {
                throw new DuplicateUserNameException(
                        "Username already exists"
                );
            }

            log.error(
                    "Failed to create node for username: {}",
                    personDto.getUserName(),
                    ex
            );

            throw ex;
        }
    }

}

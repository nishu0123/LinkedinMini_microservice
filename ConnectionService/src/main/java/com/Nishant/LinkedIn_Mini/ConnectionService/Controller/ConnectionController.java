package com.Nishant.LinkedIn_Mini.ConnectionService.Controller;

import com.Nishant.LinkedIn_Mini.ConnectionService.Dto.PersonDto;
import com.Nishant.LinkedIn_Mini.ConnectionService.Dto.UserInfoDto;
import com.Nishant.LinkedIn_Mini.ConnectionService.Service.ConnectionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/people")
public class ConnectionController {

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ConnectionService connectionService;

    @GetMapping("/greet")
    public String greet()
    {
        return "welcome to connection service!!!";
    }

    @GetMapping("/{userId}/first-degree")
    public List<PersonDto> getFirstDegreeConnection(@PathVariable Long userId)
    {
        //for the given userId i have to fetch the first degree connection


        List<PersonDto> allConnection = connectionService.getFirstDegreeConnection(userId);

        log.info("connection data received  from service in controller = " + allConnection);
        return allConnection;

    }

    @GetMapping("/{userId}/second-degree")
    public List<PersonDto> getSecondDegreeConnection(@PathVariable Long userId)
    {
        //for the given userId i have to fetch the second degree connection
        List<PersonDto> allConnection = connectionService.geSecondDegreeConnection(userId);

        log.info("connection data received  from service in controller = " + allConnection);
        return allConnection;

    }



    @GetMapping("/{userId}/third-degree")
    public List<PersonDto> getThirdDegreeConnection(@PathVariable Long userId)
    {
        //for the given userId i have to fetch the third degree connection
        List<PersonDto> allConnection = connectionService.getThirdDegreeConnection(userId);

        log.info("connection data received  from service in controller = " + allConnection);
        return allConnection;

    }

    @PostMapping("/addUserNode")
    public ResponseEntity<PersonDto> addUserNode(@RequestBody PersonDto personDto)
    {
        //implement the logic here
        //in the request dto i am expecting the unsername and email
        //node will consist of username and userId ,
        //we will receive username and the email from userService
        //here i will create the userId
        //then i will create node that will consist of the userid and the username
        PersonDto personDtoresponse = connectionService.addUserNode(personDto);

        //Return the response
        return new ResponseEntity<>(personDtoresponse ,HttpStatus.CREATED);

    }
}

package com.Nishant.LinkedIn_Mini.ConnectionService.Controller;

import com.Nishant.LinkedIn_Mini.ConnectionService.Dto.PersonDto;
import com.Nishant.LinkedIn_Mini.ConnectionService.Service.ConnectionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

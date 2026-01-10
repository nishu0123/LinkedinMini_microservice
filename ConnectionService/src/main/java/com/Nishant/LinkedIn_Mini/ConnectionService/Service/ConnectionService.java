package com.Nishant.LinkedIn_Mini.ConnectionService.Service;

import com.Nishant.LinkedIn_Mini.ConnectionService.Dto.PersonDto;
import com.Nishant.LinkedIn_Mini.ConnectionService.Entity.PersonEntity;
import com.Nishant.LinkedIn_Mini.ConnectionService.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionService {

    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final PersonRepository personRepository;

    public List<PersonDto> getFirstDegreeConnection(Long userId) {
        List<PersonEntity> connections =
                personRepository.findFirstDegreeConnections(userId);

        log.info("Fetched connections size: {}", connections.size());
        log.info("connection data received  from repository in service = " + connections.get(0));

        List<PersonDto> allPersonDto = new ArrayList<>();




        for(PersonEntity value :  connections)
        {
            PersonDto personDtovalue = modelMapper.map(value , PersonDto.class);
            log.info("person " + value.toString());
            allPersonDto.add(personDtovalue);
        }
        return allPersonDto;

//        return connections.stream()
//                .map(connection -> modelMapper.map(connection, PersonDto.class))
//                .toList();


        /*
        Optional<PersonEntity> allConnection = personRepository.findByUserId(userId);


        Optional<PersonEntity> allConnection1 = personRepository.findById(1L); // i have hardcode the value
        log.info("ConnectionService logs : fetched all connection using id " + allConnection1);


        //checked the log its empty , getching data from the databse is not working
        log.info("ConnectionService logs : fetched all connection using usereId " + allConnection);

        //repository will return the list of all the person and we will map it with the PersonDto
        //and then we will return this .

        //at this time returning the dummy data checking if the data has been fetched correctly or not
        List<PersonDto> dummyReturn = List.of(new PersonDto());
        return dummyReturn;

        */
    }

    public List<PersonDto> getThirdDegreeConnection(Long userId) {
        List<PersonEntity> connections =
                personRepository.findThirdDegreeConnections(userId);

        log.info("Fetched connections size: {}", connections.size());
        log.info("connection data received  from repository in service = " + connections.get(0));

        List<PersonDto> allPersonDto = new ArrayList<>();




        for(PersonEntity value :  connections)
        {
            PersonDto personDtovalue = modelMapper.map(value , PersonDto.class);
            log.info("person " + value.toString());
            allPersonDto.add(personDtovalue);
        }
        return allPersonDto;
    }

    public List<PersonDto> geSecondDegreeConnection(Long userId) {

        List<PersonEntity> connections =
                personRepository.findSecondDegreeConnections(userId);

        log.info("Fetched connections size: {}", connections.size());
        log.info("connection data received  from repository in service = " + connections.get(0));

        List<PersonDto> allPersonDto = new ArrayList<>();




        for(PersonEntity value :  connections)
        {
            PersonDto personDtovalue = modelMapper.map(value , PersonDto.class);
            log.info("person " + value.toString());
            allPersonDto.add(personDtovalue);
        }
        return allPersonDto;
    }
}

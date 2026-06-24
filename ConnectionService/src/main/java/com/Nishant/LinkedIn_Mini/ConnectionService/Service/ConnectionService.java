package com.Nishant.LinkedIn_Mini.ConnectionService.Service;

import com.Nishant.LinkedIn_Mini.ConnectionService.Dto.ConnectUserRequestDto;
import com.Nishant.LinkedIn_Mini.ConnectionService.Dto.ErrorResponseDto;
import com.Nishant.LinkedIn_Mini.ConnectionService.Dto.PersonDto;
import com.Nishant.LinkedIn_Mini.ConnectionService.Dto.UserInfoDto;
import com.Nishant.LinkedIn_Mini.ConnectionService.Entity.PersonEntity;
import com.Nishant.LinkedIn_Mini.ConnectionService.Exception.ConnectionOperationException;
import com.Nishant.LinkedIn_Mini.ConnectionService.Exception.DuplicateUserNameException;
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
        //below line will give out of bound if there is no any connection
//        log.info("connection data received  from repository in service = " + connections.get(0));

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

    public PersonDto addUserNode(PersonDto personDtoRequest) {
        //create the personDto
        PersonEntity personEntity = new PersonEntity();
        personEntity.setUserId(personDtoRequest.getUserId());
        personEntity.setUserName(personDtoRequest.getUserName());

        try
        {
            personRepository.save(personEntity);
        } catch (RuntimeException e) {
            throw new DuplicateUserNameException(
                    "Username already exists"
            );
        }

        return personDtoRequest;

    }

    public void unfollowUser(Long sourceUserId, ConnectUserRequestDto connectUserRequestDto) {
        //here we will use the try catch block if operation in the database will not be
        //successful the exception will be thrown with the custom message and the status code
        Long destinationUserId = connectUserRequestDto.getDestinationUserId();
        try
        {
            log.info("unfollowUser request reached to the service layer");
            personRepository.unfollow(sourceUserId , destinationUserId);
            log.info("unfollowUser request return back from the service layer");

        }catch (Exception e){
//            return new ErrorResponseDto(e.getStackTrace().toString() , "something went wrong");
            log.error(
                    "Failed to process unfollow request. sourceUserId={}, destinationUserId={}",
                    sourceUserId,
                    destinationUserId,
                    e);

            throw new ConnectionOperationException(
                    "Unable to unfollow  at this time. Try after some time",
                    e);
        }

    }

    public void rejectConnection(Long sourceUserId, ConnectUserRequestDto connectUserRequestDto) {
        Long destinationUserId = connectUserRequestDto.getDestinationUserId();
        try{
            personRepository.rejectConnection(sourceUserId , destinationUserId);

        }catch(Exception e){
            log.error(
                    "Failed to reject connection request. sourceUserId={}, destinationUserId={}",
                    sourceUserId,
                    destinationUserId,
                    e);

            throw new ConnectionOperationException(
                    "Unable to reject connection request at this time.",
                    e);
        }
    }

    public void acceptConnection(Long sourceUserId, ConnectUserRequestDto connectUserRequestDto) {
        Long destinationUserId = connectUserRequestDto.getDestinationUserId();
        try{
            personRepository.acceptConnection(sourceUserId , destinationUserId);

        }catch(Exception e){
            log.error(
                    "Failed to accept connection request. sourceUserId={}, destinationUserId={}",
                    sourceUserId,
                    destinationUserId,
                    e);

            throw new ConnectionOperationException(
                    "Unable to accept connection request at this time.",
                    e);
        }
    }

    public void connectionRequest(Long sourceUserId, ConnectUserRequestDto connectUserRequestDto) {
        Long destinationUserId = connectUserRequestDto.getDestinationUserId();
        try{
            personRepository.connectionRequest(sourceUserId , destinationUserId);

        }catch(Exception e){
            log.error(
                    "Failed to create connection request. sourceUserId={}, destinationUserId={}",
                    sourceUserId,
                    destinationUserId,
                    e);

            throw new ConnectionOperationException(
                    "Unable to create connection request at this time.",
                    e);
        }

    }
}

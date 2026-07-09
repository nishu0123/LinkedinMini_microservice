package com.Nishant.LinkedIn_Mini.ConnectionService.Controller;

import com.Nishant.LinkedIn_Mini.ConnectionService.Dto.ConnectUserRequestDto;
import com.Nishant.LinkedIn_Mini.ConnectionService.Dto.ConnectUserResponseDto;
import com.Nishant.LinkedIn_Mini.ConnectionService.Service.ConnectionService;
import com.Nishant.LinkedIn_Mini.ConnectionService.Util.ResponseBuilder;
import com.nishant.linkedinmini.common.contracts.ApiResponse;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.PersonDto;
import jakarta.validation.Valid;
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

    @GetMapping("/viewConnection")
    public ResponseEntity<ApiResponse<List<PersonDto>>> viewConnection(@RequestHeader("X-User-Id") Long userId)
    {
        //for the given userId i have to fetch the first degree connection

        List<PersonDto> allConnection = connectionService.getFirstDegreeConnection(userId);

        log.info("connection data received  from service in controller = " + allConnection);

//        return allConnection;
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBuilder.buildSuccessResponse(
                        HttpStatus.OK,
                        "connection fetched successfully",
                        allConnection
                ));

    }

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<ApiResponse<List<PersonDto>>> getFirstDegreeConnection(@PathVariable Long userId)
    {
        //for the given userId i have to fetch the first degree connection


        List<PersonDto> allConnection = connectionService.getFirstDegreeConnection(userId);

        log.info("connection data received  from service in controller = " + allConnection);
//        return allConnection;
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBuilder.buildSuccessResponse(
                        HttpStatus.OK,
                        "first-degree connection fetched successfully",
                        allConnection
                ));

    }

    @GetMapping("/{userId}/second-degree")
    public ResponseEntity<ApiResponse<List<PersonDto>>> getSecondDegreeConnection(@PathVariable Long userId)
    {
        //for the given userId i have to fetch the second degree connection
        List<PersonDto> allConnection = connectionService.getSecondDegreeConnection(userId);

        log.info("connection data received  from service in controller = " + allConnection);
//        return allConnection;
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBuilder.buildSuccessResponse(
                        HttpStatus.OK,
                        "Second-degree connection fetched successfully",
                        allConnection
                ));

    }



    @GetMapping("/{userId}/third-degree")
    public ResponseEntity<ApiResponse<List<PersonDto>>> getThirdDegreeConnection(@PathVariable Long userId)
    {
        //for the given userId i have to fetch the third degree connection
        List<PersonDto> allConnection = connectionService.getThirdDegreeConnection(userId);

        log.info("connection data received  from service in controller = " + allConnection);
//        return allConnection;
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBuilder.buildSuccessResponse(
                        HttpStatus.OK,
                        "Third-degree connection fetched successfully",
                        allConnection
                ));
    }

    @PostMapping("/addUserNode")
    public ResponseEntity<ApiResponse<PersonDto>> addUserNode(@Valid @RequestBody PersonDto personDto)
    {
        //adding a node of user whose have signed in to the system
        PersonDto personDtoresponse = connectionService.addUserNode(personDto);

        //Return the response
//        return new ResponseEntity<>(personDtoresponse ,HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.buildSuccessResponse(
                        HttpStatus.CREATED,
                        "user node added successfully",
                        personDtoresponse
                ));
    }


    //Api connect
    //requestDto - contains destinationUserId - userid neverchanges username may change
    //responsedto - contains both sourceUserId and destinationUserId to give information
    //about the connection
    @PostMapping("/sendConnectionRequest")
    ResponseEntity<ApiResponse<ConnectUserResponseDto>> connectionRequest(@RequestHeader("X-User-Id") Long sourceUserId ,@Valid @RequestBody ConnectUserRequestDto connectUserRequestDto)
    {
        //implement the api for making connection between two user
        //at this time keep the relationship type - REQUESTED
        connectionService.connectionRequest(sourceUserId , connectUserRequestDto);


        ConnectUserResponseDto connectUserResponseDto = new ConnectUserResponseDto();
        connectUserResponseDto.setDestinationUserId(connectUserRequestDto.getDestinationUserId());
        connectUserResponseDto.setSourceUserId(sourceUserId);

//        return new ResponseEntity<>(connectUserResponseDto , HttpStatus.ACCEPTED);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBuilder.buildSuccessResponse(
                        HttpStatus.OK,
                        "Connection request sent successfully",
                        connectUserResponseDto
                ));
    }


    //API for AcceptConnectionRequest
    @PostMapping("/acceptConnectionRequest")
    ResponseEntity<ApiResponse<ConnectUserResponseDto>> acceptConnection(@RequestHeader("X-User-Id") Long sourceUserId , @Valid @RequestBody ConnectUserRequestDto connectUserRequestDto)
    {
        //implement the api to accept the connection request and update graph database neo4j accordingly
        //now delete the relationship type REQUESTED
        //and add relatioship type CONNECTEDTO and make connection bi-directional
        connectionService.acceptConnection(sourceUserId , connectUserRequestDto);
        ConnectUserResponseDto connectUserResponseDto = new ConnectUserResponseDto();
        connectUserResponseDto.setDestinationUserId(connectUserRequestDto.getDestinationUserId());
        connectUserResponseDto.setSourceUserId(sourceUserId);


//        return new ResponseEntity<>(connectUserResponseDto , HttpStatus.ACCEPTED);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBuilder.buildSuccessResponse(
                        HttpStatus.OK,
                        "Connection request accepted successfully",
                        connectUserResponseDto
                ));
    }

    //API for RejectConnectionRequest
    @PostMapping("/rejectConnectionRequest")
    ResponseEntity<ApiResponse<ConnectUserResponseDto>> rejectConnection(@RequestHeader("X-User-Id") Long sourceUserId,@Valid @RequestBody ConnectUserRequestDto connectUserRequestDto)
    {
        //implement the api to accept the connection request and update graph database neo4j accordingly
        //delete the relationship type - REQUESTED_TO
        connectionService.rejectConnection(sourceUserId , connectUserRequestDto);

        ConnectUserResponseDto connectUserResponseDto = new ConnectUserResponseDto();
        connectUserResponseDto.setDestinationUserId(connectUserRequestDto.getDestinationUserId());
        connectUserResponseDto.setSourceUserId(sourceUserId);

//        return new ResponseEntity<>(connectUserResponseDto , HttpStatus.ACCEPTED);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBuilder.buildSuccessResponse(
                        HttpStatus.OK,
                        "Connection request rejected",
                        connectUserResponseDto
                ));
    }


    //API for unfollowUser - disconnect from the already connected user
    @PostMapping("/unfollowUser")
    ResponseEntity<ApiResponse<ConnectUserResponseDto>> unfollowUser(@RequestHeader("X-User-Id") Long sourceUserId , @Valid @RequestBody ConnectUserRequestDto connectUserRequestDto)
    {
        //implement the api to accept the connection request and update graph database neo4j accordingly
        //remove the connection between them

        log.info("unfollowUser request reached to the controller");
        connectionService.unfollowUser(sourceUserId , connectUserRequestDto);

        ConnectUserResponseDto connectUserResponseDto = new ConnectUserResponseDto();
        connectUserResponseDto.setDestinationUserId(connectUserRequestDto.getDestinationUserId());
        connectUserResponseDto.setSourceUserId(sourceUserId);

//        return new ResponseEntity<>(connectUserResponseDto , HttpStatus.ACCEPTED);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBuilder.buildSuccessResponse(
                        HttpStatus.OK,
                        "user unfollowed successfully",
                        connectUserResponseDto
                ));
    }

}

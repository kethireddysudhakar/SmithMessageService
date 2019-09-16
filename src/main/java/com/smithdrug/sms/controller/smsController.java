package com.smithdrug.sms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smithdrug.sms.emailService.emailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/smsServices")
@Api(value="SMSStore", description="Operations pertaining to Smith Messaging Web Services")
@Validated
public class smsController {
	
	@Autowired
	private emailService smsService;

	@ApiOperation(value = "Quick check if the service is up and running",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully tested the service availability"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
	@GetMapping(path = "/serviceCheck")
	public String quickServiceChecker() {
        return "Service Up and Running";
	}
	
	@ApiOperation(value = "Reset request for user password",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 400, message = "Data validation failed"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
	@GetMapping(path = "/v1/sendFPSEmail/{emailAddress}/{uniqueId}/{user}/{company}")
	public ResponseEntity<?> sendFPSEmail(@PathVariable @NotNull String emailAddress,@PathVariable @NotNull String uniqueId,@PathVariable @NotNull String user,@PathVariable @NotNull String company,HttpServletRequest request)
	{
		boolean emailForUser = false;
		if(company.equalsIgnoreCase("egate"))
		{
			emailForUser = smsService.sendPasswordResetEmailForEgate(emailAddress, uniqueId,user,request);
		}
		if(company.equalsIgnoreCase("integral"))
		{
			emailForUser = smsService.sendPasswordResetEmailForIntegral(emailAddress, uniqueId,user,request);
		}
		
		if(emailForUser)
		{
			return new ResponseEntity<>("Email Sent Sucessfully", HttpStatus.OK);
		}
		else
		{
			
			return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@ApiOperation(value = "Reset request for user password",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 400, message = "Data validation failed"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
	@GetMapping(path = "/v1/sendFPSSuccessEmail/{emailAddress}/{user}/{company}")
	public ResponseEntity<?> sendFPSSuccessEmail(@PathVariable @NotNull String emailAddress,@PathVariable @NotNull String user,@PathVariable @NotNull String company,HttpServletRequest request)
	{
		boolean emailForUser = false;
		if(company.equalsIgnoreCase("egate"))
		{
			emailForUser = smsService.sendPasswordResetSuccessEmailForEgate(emailAddress, user,request);
		}
		if(company.equalsIgnoreCase("integral"))
		{
			emailForUser = smsService.sendPasswordResetSuccessEmailForIntegral(emailAddress, user,request);
		}
		
		if(emailForUser)
		{
			return new ResponseEntity<>("Email Sent Sucessfully", HttpStatus.OK);
		}
		else
		{
			
			return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
		}
		
	}
}

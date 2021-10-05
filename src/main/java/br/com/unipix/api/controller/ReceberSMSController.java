package br.com.unipix.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.unipix.api.dto.request.SMSRequest;
import br.com.unipix.api.service.ReceberSMSService;

@CrossOrigin
@RestController
public class ReceberSMSController {

	@Autowired
	private ReceberSMSService receberSMSService;
	
	@PostMapping(value = "/enviar")
	public void receberSMS(@RequestBody SMSRequest request) throws JsonProcessingException {
		receberSMSService.enviarSMS(request);
	}

}

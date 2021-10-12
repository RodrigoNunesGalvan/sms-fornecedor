package br.com.unipix.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.unipix.api.dto.request.SMSRequest;
import br.com.unipix.api.dto.request.SMSResponse;
import br.com.unipix.api.service.EnviarSMSService;

@CrossOrigin
@RestController
public class EnviarSMSController {

	@Autowired
	private EnviarSMSService service;
	
	@PostMapping(value = "/enviar")
	public List<SMSResponse> receberSMS(@RequestBody SMSRequest request) throws JsonProcessingException {
		return service.enviarSMS(request);
	}

}

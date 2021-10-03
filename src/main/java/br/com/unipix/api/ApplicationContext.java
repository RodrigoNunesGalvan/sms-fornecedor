package br.com.unipix.api;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.unipix.api.dto.request.SMSRequest;
import br.com.unipix.api.fornecedor.EnviadorSMS;
import br.com.unipix.api.fornecedor.EnviadorSMSFactory;

@EnableEurekaClient
@SpringBootApplication
public class ApplicationContext implements CommandLineRunner {

	@Autowired
	private EnviadorSMSFactory enviadorSMSFactory;
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationContext.class, args);
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		List<SMSRequest> lista = obterListaSMS();
		EnviadorSMS enviador = enviadorSMSFactory.obterFornecedor(2);
		enviador.prepararEnviar(lista);
	}
	
	private List<SMSRequest> obterListaSMS() {
		List<SMSRequest> lista =  new ArrayList<>();
		SMSRequest sms = new SMSRequest();
		UUID uuid = UUID.randomUUID();
		sms.setId(uuid.toString());
		sms.setMensagem("Teste de Envio SMS, cade o kafka");
		sms.setNumero("557391412560");
		lista.add(sms);
//		sms = new SMSRequest();
//		uuid = UUID.randomUUID();
//		sms.setId(uuid.toString());
//		sms.setMensagem("Teste de Envio SMS");
//		sms.setNumero("5573991412560");
//		lista.add(sms);
		return lista;
	}
}

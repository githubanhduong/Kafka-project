package jmaster.io.accountservice.controller;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jmaster.io.accountservice.model.AccountDTO;
import jmaster.io.accountservice.model.MessageDTO;
import jmaster.io.accountservice.model.StatisticDTO;
import jmaster.io.accountservice.repo.AccountRepo;
import jmaster.io.accountservice.repo.MessageRepo;
import jmaster.io.accountservice.repo.StatisticRepo;

@RestController
@RequestMapping("/account")
public class AccountController {
	private static Logger log = LogManager.getLogger(AccountController.class);
	
	@Autowired
	MessageDTO messageDTO;
	
	@Autowired
	StatisticDTO statisticDTO;
	
	@Autowired
	AccountRepo accountRepo;
	
	@Autowired
	StatisticRepo statisticRepo;
	
	@Autowired
	MessageRepo messageRepo;
	
	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;
	
	@PostMapping("/new")
	public AccountDTO create(@RequestBody AccountDTO accountDTO) {
		statisticDTO.setCreatedDate(new Date());
		statisticDTO.setMessage(accountDTO.toString());
		statisticDTO.setStatus(false);
		
		messageDTO.setTo(accountDTO.getEmail());
		messageDTO.setToName(accountDTO.getName());
		messageDTO.setSubject("welcome jmaster");
		messageDTO.setContent("jmaster is ...");
		messageDTO.setStatus(false);

		accountRepo.save(accountDTO);
		statisticRepo.save(statisticDTO);
		messageRepo.save(messageDTO);
//		for (int i = 0; i < 100; i++) {
//			CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("notification", messageDTO);
//			future.whenComplete((result, error) -> {
//				if (error != null) {
//					error.printStackTrace();
//				} else {
//					log.info("Succes XXX: " + result.getRecordMetadata().partition());
//				}
//			});
//		}
//		kafkaTemplate.send("statistics", statisticDTO);
		return accountDTO;
	}
	
}

package jmaster.io.accountservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jmaster.io.accountservice.model.MessageDTO;
import jmaster.io.accountservice.model.StatisticDTO;
import jmaster.io.accountservice.repo.MessageRepo;
import jmaster.io.accountservice.repo.StatisticRepo;

@Component
public class PollingService {
	private Logger logger = LoggerFactory.getLogger(PollingService.class);
	
	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;
	
	@Autowired
	MessageRepo messageRepo;
	
	@Autowired
	StatisticRepo statisticRepo;
	
	@Scheduled(fixedDelay = 1000)
	public void producer() {
		List<MessageDTO> messageDTOs = new ArrayList<MessageDTO>();
		
		for (MessageDTO messageDTO :messageDTOs) {
			CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("notification", messageDTO);
			future.whenComplete((result, error) -> {
				if (error != null) {
					error.printStackTrace();
					logger.error("Failed to send notification " + error);
				} else {
					messageDTO.setStatus(true);
					messageRepo.save(messageDTO);
					logger.info("Succes XXX: " + result.getRecordMetadata().partition());
				}
			});
		}
		
		//
		List<StatisticDTO> statisticDTOs = statisticRepo.findByStatus(false);
		for (StatisticDTO statisticDTO : statisticDTOs) {
			CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("statistics", statisticDTO);
			future.whenComplete((result, error) -> {
				if (error != null) {
					error.printStackTrace();
					logger.error("Failed to send notification " + error);
				} else {
					statisticDTO.setStatus(true);
					statisticRepo.save(statisticDTO);
					logger.info("Succes XXX: " + result.getRecordMetadata().partition());
				}
			});
		}
		
	}

	@Scheduled(fixedDelay = 60000)
	public void delete() {
		logger.info("delete all");
		List<MessageDTO> messageDTOs = messageRepo.findByStatus(true);
		List<StatisticDTO> statisticDTOs = statisticRepo.findByStatus(true);
		
		messageRepo.deleteAllInBatch(messageDTOs);
        statisticRepo.deleteAllInBatch(statisticDTOs);
	}
}

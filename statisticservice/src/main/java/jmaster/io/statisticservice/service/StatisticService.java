package jmaster.io.statisticservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import jmaster.io.statisticservice.entiy.Statistic;
import jmaster.io.statisticservice.repository.StatisticRepo;

@Service
public class StatisticService {
	private final Logger logger = LoggerFactory.getLogger(StatisticService.class);
	
	@Autowired
	private StatisticRepo statisticRepo;
	
//	@RetryableTopic(attempts = "5", dltTopicSuffix = "-dlt",
//			backoff = @Backoff(delay = 2_000, multiplier = 2))
	@KafkaListener(id = "statisticsGroup", topics = "statistics")
	public void listen(Statistic statistic) {
		logger.info("Received Statistic" + statistic.getMessage());
//		statisticRepo.save(statistic);
		throw new RuntimeException();
	}
	
	@KafkaListener(id = "dltGroup", topics = "statistics-dlt")
	public void dltlisten(Statistic statistic) {
		logger.info("Received Statistic dlt " + statistic.getMessage());
	}
}

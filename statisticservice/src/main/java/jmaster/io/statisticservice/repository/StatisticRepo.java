package jmaster.io.statisticservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jmaster.io.statisticservice.entiy.Statistic;

public interface StatisticRepo extends JpaRepository<Statistic, Integer>{

}

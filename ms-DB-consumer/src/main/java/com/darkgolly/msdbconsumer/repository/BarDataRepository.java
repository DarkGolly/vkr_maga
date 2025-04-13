package com.darkgolly.msdbconsumer.repository;

import com.darkgolly.msdbconsumer.model.entity.BarDataEntity;
import com.darkgolly.msdbconsumer.model.entity.GpsDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarDataRepository extends JpaRepository<BarDataEntity, Long> {
}

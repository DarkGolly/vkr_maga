package com.darkgolly.msdbconsumer.repository;

import com.darkgolly.msdbconsumer.model.entity.GpsDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpsDataRepository extends JpaRepository<GpsDataEntity, Long> {
}
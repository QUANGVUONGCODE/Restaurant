package com.store.restaurant.repository;

import com.store.restaurant.entity.Revervation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Revervation, Long> {
//    @Query("SELECT r FROM Reservation r WHERE r.status = :status AND r.startTime <= :currentTime")
//    List<Revervation> findByStatusAndStartTimeBefore(
//            @Param("status") String status,
//            @Param("currentTime") LocalDateTime startTime);

    @Query("SELECT r FROM Revervation r WHERE r.table.id = :tableId " +
            "AND ((r.startTime BETWEEN :startTime AND :endTime) " +
            "OR (r.endTime BETWEEN :startTime AND :endTime) " +
            "OR (r.startTime <= :startTime AND r.endTime >= :endTime))")
    List<Revervation> findByTableIdAndTimeRange(@Param("tableId") Long tableId,
                                                @Param("startTime") LocalDateTime startTime,
                                                @Param("endTime") LocalDateTime endTime);


}

package com.store.restaurant.service;

import com.store.restaurant.entity.Revervation;
import com.store.restaurant.repository.ReservationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RevervationService {
//    ReservationRepository reservationRepository;
//
//    @Scheduled(cron = "0 0 7-24 * * *")
//    public void checkAndUpdateReservationStatus(){
//        LocalDateTime now = LocalDateTime.now();
//        List<Revervation> reservations = reservationRepository.findByStatusAndStartTimeBefore("SERVICE", now);
//        for (Revervation revervation : reservations){
//            revervation.setStatus("SERVICE");
//            reservationRepository.save(revervation);
//        }
//    }
}

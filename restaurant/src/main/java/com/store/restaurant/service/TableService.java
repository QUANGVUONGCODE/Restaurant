package com.store.restaurant.service;

import com.store.restaurant.dto.request.TableRequest;
import com.store.restaurant.dto.response.TableResponse;
import com.store.restaurant.entity.Revervation;
import com.store.restaurant.entity.Table;
import com.store.restaurant.enums.TableStatus;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.mapper.TableMapper;
import com.store.restaurant.repository.ReservationRepository;
import com.store.restaurant.repository.TableRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TableService {
    TableRepository tableRepository;
    TableMapper tableMapper;
    ReservationRepository reservationRepository;

    public TableResponse createTable(TableRequest request){
        if(tableRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.TABLE_EXISTS);
        }
        Table table = tableMapper.mapToTable(request);
        table.setStatus(TableStatus.AVAILABLE.name());
        table.setActive(true);
        return tableMapper.mapTableResponse(tableRepository.save(table));
    }

    public TableResponse getTableById(Long id){
        return tableMapper.mapTableResponse(tableRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ID_TABLE_NOT_FOUND)));
    }

    public List<TableResponse> getAll(){
        return tableRepository.findAll().stream().map(tableMapper::mapTableResponse).toList();
    }



    public TableResponse updateTable(TableRequest request, Long id){
        Table table = tableRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ID_TABLE_NOT_FOUND));
        tableMapper.updateTable(request, table);

        return tableMapper.mapTableResponse(tableRepository.save(table));
    }


    public void deleteTable(Long id){
        Table table = tableRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ID_TABLE_NOT_FOUND));
        table.setActive(false);
        tableRepository.save(table);
    }


    public List<TableResponse> getAllTables(LocalDateTime startTime, LocalDateTime endTime) {
        // Lấy tất cả các bàn
        List<Table> allTables = tableRepository.findAll();


        // Lọc các bàn có thể đặt (không bị trùng lịch với thời gian yêu cầu)
        return allTables.stream()
                .filter(table -> isTableAvailableForTimeRange(table.getId(), startTime, endTime))
                .map(tableMapper::mapTableResponse)
                .collect(Collectors.toList());
    }

    public boolean isTableAvailableForTimeRange(Long tableId, LocalDateTime startTime, LocalDateTime endTime) {
        // Lấy danh sách các đặt chỗ của bàn trong khoảng thời gian người dùng yêu cầu
        List<Revervation> reservations = reservationRepository.findByTableIdAndTimeRange(tableId, startTime, endTime);

        log.debug("Checking availability for tableId: {} between {} and {}", tableId, startTime, endTime);

        // Kiểm tra nếu bàn đã được đặt trong khoảng thời gian này
        for (Revervation reservation : reservations) {
            LocalDateTime reservedStart = reservation.getStartTime();
            LocalDateTime reservedEnd = reservation.getEndTime();

            log.info("Reserved Start: {}", reservedStart);
            log.info("Reserved End: {}", reservedEnd);

            // Kiểm tra các điều kiện sau:
            // 1. Thời gian bắt đầu của đặt bàn mới phải cách thời gian bắt đầu đặt bàn cũ ít nhất 6 tiếng
            // 2. Thời gian kết thúc của đặt bàn mới phải trước ít nhất 1 tiếng so với thời gian bắt đầu đặt bàn cũ
            // 3. Thời gian kết thúc của đặt bàn mới phải sau ít nhất 2 tiếng so với thời gian kết thúc đặt bàn cũ
            if (!(startTime.isBefore(reservedStart.minusHours(6)) &&
                    endTime.isBefore(reservedStart.minusHours(1)) &&
                    startTime.isAfter(reservedEnd.plusHours(1)))) {
                log.info("Table is not available for the requested time range.");
                return false; // Bàn đã có người đặt hoặc không thỏa các điều kiện
            }
        }

        // Nếu không có đặt chỗ nào chồng lấn và thỏa các điều kiện thời gian, bàn sẽ trống
        log.info("Table is available for the requested time range.");
        return true;
    }

    public Long countTables(){
        return tableRepository.count();
    }
}

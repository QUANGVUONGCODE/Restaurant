package com.store.restaurant.controller;

import com.store.restaurant.dto.request.TableRequest;
import com.store.restaurant.dto.response.ApiResponse;
import com.store.restaurant.dto.response.TableResponse;
import com.store.restaurant.service.TableService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/tables")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TableController {
    TableService tableService;

    @PostMapping
    ApiResponse<TableResponse> createTable(@RequestBody TableRequest request){
        return ApiResponse.<TableResponse>builder()
                .result(tableService.createTable(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<TableResponse>> getAllTables(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startTime,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endTime
    ) {

        // Nếu không truyền startTime → mặc định 8:00 sáng hôm nay
        if (startTime == null) {
            startTime = LocalDate.now().atTime(8, 0);
        }

        // Nếu không truyền endTime → mặc định là hiện tại
        if (endTime == null) {
            endTime = LocalDateTime.now();
        }

        return ApiResponse.<List<TableResponse>>builder()
                .result(tableService.getAllTables(startTime, endTime))
                .build();
    }


    @GetMapping("/{id}")
    ApiResponse<TableResponse> getTableById(@PathVariable Long id){
        return ApiResponse.<TableResponse>builder()
                .result(tableService.getTableById(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<TableResponse> updateTable(@RequestBody TableRequest request, @PathVariable Long id){
        return ApiResponse.<TableResponse>builder()
                .result(tableService.updateTable(request,id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteTable(@PathVariable Long id){
        tableService.deleteTable(id);
        return ApiResponse.<String>builder()
                .result("Delete Table Successfully")
                .build();
    }

    @GetMapping("/count")
    ApiResponse<Long> countTables(){
        return ApiResponse.<Long>builder()
                .result(tableService.countTables())
                .build();
    }
}

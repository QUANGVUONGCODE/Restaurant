package com.store.restaurant.controller;

import com.store.restaurant.dto.request.OrderRequest;
import com.store.restaurant.dto.request.requestUpdate.OrderUpdateRequest;
import com.store.restaurant.dto.response.ApiResponse;
import com.store.restaurant.dto.response.OrderListResponse;
import com.store.restaurant.dto.response.OrderResponse;
import com.store.restaurant.dto.response.RevenueAndOrderCount;
import com.store.restaurant.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;


    @PostMapping
    ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<OrderResponse> getOrderById(@PathVariable Long id){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrderById(id))
                .build();
    }


    @GetMapping
    ApiResponse<OrderListResponse> getAllOrders(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by("orderDate").descending()
        );
        Page<OrderResponse> orderPage = orderService.getAllOrders(keyword, pageRequest);
        List<OrderResponse> orders = orderPage.getContent();
        int totalPages = orderPage.getNumber() + 1;
        return ApiResponse.<OrderListResponse>builder()
                .result(OrderListResponse.builder()
                        .orders(orders)
                        .page(totalPages)
                        .build())
                .build();
    }

    @GetMapping("/user/{id}")
    ApiResponse<List<OrderResponse>> getOrderByUserId(@PathVariable Long id){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrderByUserId(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<OrderResponse> updateOrder(@PathVariable Long id, @RequestBody OrderUpdateRequest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrder(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ApiResponse.<String>builder()
                .result("Order deleted successfully")
                .build();
    }

    @GetMapping("/total-revenue")
    public ApiResponse<RevenueAndOrderCount> getTotalRevenue(
            @RequestParam(value = "month", defaultValue = "#{T(java.time.LocalDate).now().getMonthValue()}") int month,
            @RequestParam(value = "year", defaultValue = "#{T(java.time.LocalDate).now().getYear()}") int year){
        return ApiResponse.<RevenueAndOrderCount>builder()
                .result(orderService.getTotalOfMonth(month, year))
                .build();
    }

    @GetMapping("/total-revenue-day")
    public ApiResponse<RevenueAndOrderCount> getTotalRevenueForToday(){
        return ApiResponse.<RevenueAndOrderCount>builder()
                .result(orderService.getTotalRevenueAndOrderCountForToday())
                .build();
    }

    @GetMapping("/totalToNow")
    public ApiResponse<BigDecimal> getTotalRevenueFromPastToNow(){
        return ApiResponse.<BigDecimal>builder()
                .result(orderService.getTotalRevenueFromPastToNow())
                .build();
    }

    @PutMapping("/status/{id}")
    ApiResponse<String> updateOrderStatus(@PathVariable Long id){
        orderService.updateStatus(id);
        return ApiResponse.<String>builder()
                .result("Order status updated successfully")
                .build();
    }

    @GetMapping("/today")
    public ApiResponse<List<OrderResponse>> getOrdersForToday() {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersForToday())
                .build();
    }
}

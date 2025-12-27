package com.store.restaurant.service;

import com.store.restaurant.dto.request.OrderDetailRequest;
import com.store.restaurant.dto.response.OrderDetailResponse;
import com.store.restaurant.entity.Food;
import com.store.restaurant.entity.Order;
import com.store.restaurant.entity.OrderDetail;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.mapper.OrderDetailMapper;
import com.store.restaurant.repository.FoodRepository;
import com.store.restaurant.repository.OrderDetailRepository;
import com.store.restaurant.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailService {
    OrderDetailRepository orderDetailRepository;
    OrderDetailMapper orderDetailMapper;
    OrderRepository orderRepository;
    FoodRepository foodRepository;

    public OrderDetailResponse createOrderDetail(OrderDetailRequest request){
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ORDER_ID)
        );

        Food food = foodRepository.findById(request.getFoodId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_FOOD_ID)
        );

        OrderDetail orderDetail = orderDetailMapper.mapToOrderDetail(request);
        orderDetail.setOrder(order);
        orderDetail.setFood(food);
        orderDetail.setPrice(food.getPrice());
        orderDetail.setTotalMoney(food.getPrice() * request.getQuantity());
        return  orderDetailMapper.mapToOrderDetailResponse(orderDetailRepository.save(orderDetail));
    }

    public List<OrderDetailResponse> getOrderDetailByOrderId(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ORDER_ID)
        );
        return orderDetailRepository.findByOrderId(orderId).stream()
                .map(orderDetailMapper::mapToOrderDetailResponse)
                .toList();
    }

    @Transactional
    public void deleteOrderDetail(Long orderId){
        if(!orderRepository.existsById(orderId)){
            throw new AppException(ErrorCode.INVALID_ORDER_ID);
        }
        orderDetailRepository.deleteByOrderId(orderId);
    }
}

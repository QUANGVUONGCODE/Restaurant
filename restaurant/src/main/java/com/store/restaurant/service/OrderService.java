package com.store.restaurant.service;

import com.store.restaurant.dto.request.CartRequest;
import com.store.restaurant.dto.request.OrderRequest;
import com.store.restaurant.dto.request.requestUpdate.OrderUpdateRequest;
import com.store.restaurant.dto.response.OrderResponse;
import com.store.restaurant.dto.response.RevenueAndOrderCount;
import com.store.restaurant.entity.*;
import com.store.restaurant.enums.OrderStatus;
import com.store.restaurant.enums.TableStatus;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.mapper.OrderDetailMapper;
import com.store.restaurant.mapper.OrderMapper;
import com.store.restaurant.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    UserRepository userRepository;
    FoodRepository foodRepository;
    TableRepository tableRepository;
    OrderDetailMapper orderDetailMapper;
    OrderDetailRepository orderDetailRepository;
    OrderDetailService orderDetailService;
    PaymentRepository paymentRepository;
    ReservationRepository reservationRepository;
    TableService tableService;

    public OrderResponse createOrder(OrderRequest request){
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_USER_ID)
        );


        Table table = tableRepository.findById(request.getTableId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_TABLE_ID)
        );



        Payment payment = paymentRepository.findById(request.getPaymentId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_PAYMENT_ID)
        );
        if(request.getOrderDate().isAfter(request.getEndTime())){
            throw new AppException(ErrorCode.INVALID_TIME_RANGE);
        }

        if(request.getNumberOfGuest() > table.getCapacity()){
            throw new AppException(ErrorCode.INVALID_NUMBER_OF_GUEST);
        }

        if(!tableService.isTableAvailableForTimeRange(request.getTableId(), request.getOrderDate(), request.getEndTime())){
            throw new AppException(ErrorCode.INVALID_TIME_RANGE);
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();  // Thời gian bắt đầu của ngày hôm nay
        LocalDateTime endOfWeek = startOfToday.plusDays(7);

        Order order = orderMapper.mapToOrder(request);
        order.setUser(user);
        order.setPayment(payment);
        order.setTable(table);
        order.setActive(true);
        order.onCreate();
        order.setStatus(OrderStatus.PENDING.name());
        order = orderRepository.save(order);

        Revervation revervation = new Revervation();
        revervation.setTable(table);
        revervation.setOrder(order);
        revervation.setStartTime(request.getOrderDate());
        revervation.setEndTime(request.getEndTime());
        if (now.isBefore(request.getOrderDate())) {
            revervation.setStatus("RESERVED");  // Nếu thời gian hiện tại trước thời gian đặt bàn
        } else {
            revervation.setStatus("SERVICE");  // Nếu thời gian hiện tại đã đến, trạng thái sẽ là SERVICE
        }

        reservationRepository.save(revervation);
        Float totalMoney = 0f;
        for(CartRequest cartRequest : request.getCartItems()){
            Long foodId = cartRequest.getFoodId();
            Integer quantity = cartRequest.getQuantity();
            Food food = foodRepository.findById(foodId).orElseThrow(
                    () -> new AppException(ErrorCode.INVALID_FOOD_ID)
            );
            Float price = food.getPrice();
            if(price == null){
                throw new AppException(ErrorCode.INVALID_FOOD_PRICE);
            }
            if(quantity == null){
                throw new AppException(ErrorCode.INVALID_FOOD_QUANTITY);
            }
            OrderDetail orderDetail = orderDetailMapper.mapToOrderDetail2(cartRequest);
            orderDetail.setOrder(order);
            orderDetail.setFood(food);
            orderDetail.setPrice(price);
            orderDetail.setTotalMoney(price * quantity);
            totalMoney += price * quantity;
            orderDetailRepository.save(orderDetail);
            orderDetailMapper.mapToOrderDetailResponse(orderDetail);
        }
        order.setTotalMoney(totalMoney);

        if(request.getPaymentId() == 1 ){
            order.setStatus(OrderStatus.UNPAID.name());
        }else {
            order.setStatus(OrderStatus.PENDING.name());
        }

        orderRepository.save(order);
        return orderMapper.mapToOrderResponse(order);
    }

    public OrderResponse getOrderById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ORDER_ID)
        );
        return orderMapper.mapToOrderResponse(order);
    }

    public Page<OrderResponse> getAllOrders(String keyword, Pageable pageable){
        return orderRepository.findByKeyword(keyword, pageable).map(orderMapper::mapToOrderResponse);
    }

    public List<OrderResponse> getOrderByUserId(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_USER_ID)
        );
        return orderRepository.findByUserId(userId).stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .map(orderMapper::mapToOrderResponse)
                .toList();
    }

    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request){
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ORDER_ID)
        );
        orderMapper.updateOrder(request, order);
        if(request.getCartItems() != null && !request.getCartItems().isEmpty()){
            Float totalMoney = 0f;
            orderDetailService.deleteOrderDetail(orderId);
            for(CartRequest cartRequest : request.getCartItems()){
                Long foodId = cartRequest.getFoodId();
                Integer quantity = cartRequest.getQuantity();
                Food food = foodRepository.findById(foodId).orElseThrow(
                        () -> new AppException(ErrorCode.INVALID_FOOD_ID)
                );
                Float price = food.getPrice();
                if(price == null){
                    throw new AppException(ErrorCode.INVALID_FOOD_PRICE);
                }
                if(quantity == null){
                    throw new AppException(ErrorCode.INVALID_FOOD_QUANTITY);
                }
                OrderDetail orderDetail = orderDetailMapper.mapToOrderDetail2(cartRequest);
                orderDetail.setOrder(order);
                orderDetail.setFood(food);
                orderDetail.setPrice(price);
                orderDetail.setTotalMoney(price * quantity);
                totalMoney += price * quantity;
                orderDetailRepository.save(orderDetail);
                orderDetailMapper.mapToOrderDetailResponse(orderDetail);
            }
            order.setTotalMoney(totalMoney);
        }
        if(request.getStatus() != null){
            order.setStatus(request.getStatus().name());
            if(request.getStatus() == OrderStatus.CANCELLED){
                order.setActive(false);
            }
        }
        orderRepository.save(order);
        return orderMapper.mapToOrderResponse(order);
    }

    public void deleteOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ORDER_ID)
        );
        order.setStatus(OrderStatus.CANCELLED.name());
        order.setActive(false);
        orderRepository.save(order);
    }

    public RevenueAndOrderCount getTotalOfMonth(int month, int year){
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1,0,0,0,0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1);
        List<Order> orders = orderRepository.findByOrderDateBetween(startOfMonth, endOfMonth);
        Long totalOrders = orders.stream()
                .filter(order -> order.getStatus() != null && (order.getStatus().equals(OrderStatus.CANCELLED.name()) || order.getStatus().equals(OrderStatus.PENDING.name())))
                .count();
        Float totalMoney = orders.stream()
                .filter(order -> order.getStatus() != null && (order.getStatus().equals(OrderStatus.CANCELLED.name()) || order.getStatus().equals(OrderStatus.PENDING.name())))
                .map(Order::getTotalMoney)
                .reduce(0f, Float::sum);
        return new RevenueAndOrderCount(totalMoney, totalOrders);
    }

    public RevenueAndOrderCount getTotalRevenueAndOrderCountForToday() {
        LocalDateTime startOfToday = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1);

        List<Order> orders = orderRepository.findByOrderDateBetween(startOfToday, endOfToday);

        Long totalOrders = orders.stream()
                .filter(order -> order.getStatus() != null && (order.getStatus().equals(OrderStatus.PAID.name()) || order.getStatus().equals(OrderStatus.COMPLETED.name())))
                .count();

        Float totalRevenue = orders.stream()
                .filter(order -> order.getStatus() != null && (order.getStatus().equals(OrderStatus.PAID.name()) || order.getStatus().equals(OrderStatus.COMPLETED.name())))
                .map(Order::getTotalMoney)
                .reduce(0f, Float::sum);

        return new RevenueAndOrderCount(totalRevenue, totalOrders);
    }


    public BigDecimal getTotalRevenueFromPastToNow() {
        List<Order> orders = orderRepository.findByStatusIn(
                List.of(OrderStatus.COMPLETED.name(), OrderStatus.PAID.name())
        );

        BigDecimal totalRevenue = orders.stream()
                .map(order -> {
                    Float f = order.getTotalMoney();
                    return (f == null) ? BigDecimal.ZERO : BigDecimal.valueOf(f.doubleValue());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalRevenue;
    }

    public void updateStatus(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ORDER_ID)
        );
        order.setStatus(OrderStatus.COMPLETED.name());
        orderRepository.save(order);
    }

    public List<OrderResponse> getOrdersForToday() {
        LocalDateTime startOfToday = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1);

        List<Order> orders = orderRepository.findByOrderDateBetween(startOfToday, endOfToday);
        return orders.stream()
                .map(orderMapper::mapToOrderResponse)
                .collect(Collectors.toList());
    }
}

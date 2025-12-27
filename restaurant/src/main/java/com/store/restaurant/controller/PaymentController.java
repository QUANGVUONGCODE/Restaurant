package com.store.restaurant.controller;

import com.store.restaurant.dto.request.PaymentQueryRequest;
import com.store.restaurant.dto.request.PaymentRefundRequest;
import com.store.restaurant.dto.request.PaymentRequest;
import com.store.restaurant.dto.request.PaymentVNPAYRequest;
import com.store.restaurant.dto.response.ApiResponse;
import com.store.restaurant.dto.response.PaymentResponse;
import com.store.restaurant.dto.response.ResponseObject;
import com.store.restaurant.service.PaymentService;
import com.store.restaurant.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/payments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;
    VNPayService vnPayService;

    @PostMapping
    ApiResponse<PaymentResponse> createPayment(@RequestBody PaymentRequest request){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.createPayment(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PaymentResponse>> getAllPayment(){
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getAllPayments())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<PaymentResponse> getPaymentById(@PathVariable Long id){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.getPaymentById(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<PaymentResponse> updatePayment(@PathVariable Long id, @RequestBody PaymentRequest request){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.updatePayment(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deletePayment(@PathVariable Long id){
        paymentService.deletePayment(id);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/create_payment_url")
    public ResponseEntity<ResponseObject> createPayment(@RequestBody PaymentVNPAYRequest paymentVNPAYRequest, HttpServletRequest request){
        try{
            String paymentUrl = vnPayService.createPaymentUrl(paymentVNPAYRequest, request);
            return ResponseEntity.ok(ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .message("Create payment URL successfully")
                            .data(paymentUrl)
                    .build());
        }catch (Exception e){
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Create payment URL failed" + e.getMessage())
                    .build());
        }
    }

    @PostMapping("/query")
    public ResponseEntity<ResponseObject> queryTransaction(@RequestBody PaymentQueryRequest paymentQueryDTO, HttpServletRequest request) {
        try {
            String result = vnPayService.queryTransaction(paymentQueryDTO, request);
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Query successful")
                    .data(result)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Error querying transaction: " + e.getMessage())
                    .build());
        }
    }
    @PostMapping("/refund")
    public ResponseEntity<ResponseObject> refundTransaction(
            @Valid @RequestBody PaymentRefundRequest paymentRefundDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .message(String.join(", ", errorMessages))
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build());
        }

        try {
            String response = vnPayService.refundTransaction(paymentRefundDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Refund processed successfully")
                    .status(HttpStatus.OK)
                    .data(response)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder()
                    .message("Failed to process refund: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .data(null)
                    .build());
        }
    }

    @PostMapping("/result/{txnRef}")
    public ApiResponse<String> handlePaymentResult(@PathVariable String txnRef) {
        vnPayService.updateOrderWithVNPayTxnRef(txnRef);
        return ApiResponse.<String>builder()
                .result("Payment result processed successfully")
                .build();
    }
}

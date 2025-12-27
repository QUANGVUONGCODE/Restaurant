package com.store.restaurant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import com.store.restaurant.configuration.VNPayConfig;
import com.store.restaurant.configuration.VNPayUtils;
import com.store.restaurant.dto.request.PaymentQueryRequest;
import com.store.restaurant.dto.request.PaymentRefundRequest;
import com.store.restaurant.dto.request.PaymentVNPAYRequest;
import com.store.restaurant.entity.Order;
import com.store.restaurant.mapper.OrderMapper;
import com.store.restaurant.repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPayService {
    VNPayConfig vnPayConfig;
    VNPayUtils vnPayUtils;
    OrderMapper orderMapper;
    OrderRepository orderRepository;


    public String createPaymentUrl(PaymentVNPAYRequest request, HttpServletRequest httpRequest){
        String version = "2.1.0";
        String command = "pay";
        String orderType = "other";
        long amount = request.getAmount() * 100;
        String bankCode = request.getBankCode();
        String transactionReference = vnPayUtils.getRamdomNumber(8);
        String clientIpAddress = vnPayUtils.getIdAddress(httpRequest);
        String terminalCode = vnPayConfig.getVnpTmnCode();

        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", version);
        params.put("vnp_Command", command);
        params.put("vnp_TmnCode", terminalCode);
        params.put("vnp_Amount", String.valueOf(amount));
        params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            params.put("vnp_BankCode", bankCode);
        }
        params.put("vnp_TxnRef", transactionReference);
        params.put("vnp_OrderInfo", "Thanh toan don hang:" + transactionReference);
        params.put("vnp_OrderType", orderType);

        String locale = request.getLanguage();
        if (locale != null && !locale.isEmpty()) {
            params.put("vnp_Locale", locale);
        } else {
            params.put("vnp_Locale", "vn");
        }

        params.put("vnp_ReturnUrl", vnPayConfig.getVnpReturnUrl());
        params.put("vnp_IpAddr", clientIpAddress);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String createdDate = dateFormat.format(calendar.getTime());
        params.put("vnp_CreateDate", createdDate);

        calendar.add(Calendar.MINUTE, 15);
        String expirationDate = dateFormat.format(calendar.getTime());
        params.put("vnp_ExpireDate", expirationDate);

        List<String> sortedFieldNames = new ArrayList<>(params.keySet());
        Collections.sort(sortedFieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder queryData = new StringBuilder();
        for (Iterator<String> iterator = sortedFieldNames.iterator(); iterator.hasNext();) {
            String fieldName = iterator.next();
            String fieldValue = params.get(fieldName);

            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                queryData.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (iterator.hasNext()) {
                    hashData.append('&');
                    queryData.append('&');
                }
            }
        }

        String secureHash = vnPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        queryData.append("&vnp_SecureHash=").append(secureHash);

        return vnPayConfig.getVnpPayUrl() + "?" + queryData;
    }


    public String queryTransaction(PaymentQueryRequest queryDto, HttpServletRequest httpRequest) throws IOException {
        // Chuẩn bị tham số cho VNPay
        String requestId = vnPayUtils.getRamdomNumber(8);
        String version = "2.1.0";
        String command = "querydr";
        String terminalCode = vnPayConfig.getVnpTmnCode();
        String transactionReference = queryDto.getOrderId();
        String transactionDate = queryDto.getTransDate();
        String createDate = vnPayUtils.getCurrentDateTime();
        String clientIpAddress = vnPayUtils.getIdAddress(httpRequest);

        Map<String, String> params = new HashMap<>();
        params.put("vnp_RequestId", requestId);
        params.put("vnp_Version", version);
        params.put("vnp_Command", command);
        params.put("vnp_TmnCode", terminalCode);
        params.put("vnp_TxnRef", transactionReference);
        params.put("vnp_OrderInfo", "Check transaction result for OrderId:" + transactionReference);
        params.put("vnp_TransactionDate", transactionDate);
        params.put("vnp_CreateDate", createDate);
        params.put("vnp_IpAddr", clientIpAddress);

        // Tạo chuỗi hash và chữ ký bảo mật
        String hashData = String.join("|", requestId, version, command,
                terminalCode, transactionReference, transactionDate, createDate, clientIpAddress, "Check transaction");
        String secureHash = vnPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        params.put("vnp_SecureHash", secureHash);

        // Gửi yêu cầu API đến VNPay
        URL apiUrl = new URL(vnPayConfig.getVnpPayUrl());
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
            writer.writeBytes(new Gson().toJson(params));
        }

        int responseCode = connection.getResponseCode();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            if (responseCode == 200) {
                return response.toString();
            } else {
                throw new RuntimeException("VNPay API Error: " + response.toString());
            }
        }
    }

    public String refundTransaction(PaymentRefundRequest refundRequest) throws IOException {
        String requestId = vnPayUtils.getRamdomNumber(8); // Unique request ID
        String version = "2.1.0"; // API version
        String command = "refund"; // Refund command
        String terminalCode = vnPayConfig.getVnpTmnCode(); // Terminal code

        // Build VNPay parameters
        Map<String, String> params = new LinkedHashMap<>();
        params.put("vnp_RequestId", requestId);
        params.put("vnp_Version", version);
        params.put("vnp_Command", command);
        params.put("vnp_TmnCode", terminalCode);
        params.put("vnp_TransactionType", refundRequest.getTransactionType());
        params.put("vnp_TxnRef", refundRequest.getOrderId());
        params.put("vnp_Amount", String.valueOf(refundRequest.getAmount() * 100)); // Amount in smallest currency unit
        params.put("vnp_OrderInfo", "Refund for OrderId: " + refundRequest.getOrderId());
        params.put("vnp_TransactionDate", refundRequest.getTransactionDate());
        params.put("vnp_CreateBy", refundRequest.getCreatedBy());
        params.put("vnp_IpAddr", refundRequest.getIpAddress());

        // Generate secure hash
        String hashData = String.join("|",
                requestId,
                version,
                command,
                terminalCode,
                refundRequest.getTransactionType(),
                refundRequest.getOrderId(),
                String.valueOf(refundRequest.getAmount() * 100),
                refundRequest.getTransactionDate(),
                refundRequest.getCreatedBy(),
                refundRequest.getIpAddress(),
                "Refund for OrderId: " + refundRequest.getOrderId());
        String secureHash = vnPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        params.put("vnp_SecureHash", secureHash);

        // Send request to VNPay
        URL apiUrl = new URL(vnPayConfig.getVnpPayUrl());
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] jsonPayload = new ObjectMapper().writeValueAsBytes(params);
            outputStream.write(jsonPayload, 0, jsonPayload.length);
        }

        // Read response
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed to process refund. Response code: " + responseCode);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseBuilder.append(line.trim());
            }
            return responseBuilder.toString();
        }
    }

    public void updateOrderWithVNPayTxnRef(String txnRef) {
        Order order = orderRepository.findByVnpTxnRef(txnRef);
        order.setStatus("PAID");
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }
}

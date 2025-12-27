package com.store.restaurant.configuration;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPayUtils {
    VNPayConfig vnPayConfig;

    public VNPayUtils(VNPayConfig vnPayConfig) {
        this.vnPayConfig = vnPayConfig;
    }

    public String getCurrentDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        return formatter.format(cld.getTime());
    }

    public String hashAllFields(Map<String, String> fields){
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        for(Iterator<String> itr = fieldNames.iterator(); itr.hasNext();){
            String fieldName = itr.next();
            String fieldValue = fields.get(fieldName);
            if((fieldValue != null) && (!fieldValue.isEmpty())){
                sb.append(fieldName).append("=").append(fieldValue);
            }
            if(itr.hasNext()){
                sb.append("&");
            }
        }
        return hmacSHA512(vnPayConfig.getSecretKey(), sb.toString());
    }

    public String hmacSHA512(String key, String data){
        try{
            if(key == null || data == null){
                throw new NullPointerException();
            }
            Mac hmac512 = Mac.getInstance("HmacShA512");
            byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKeySpec);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        }catch (Exception e){
            return "";
        }
    }

    public String getRamdomNumber(int len){
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++){
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public String getIdAddress(HttpServletRequest request){
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        return (ipAddress != null && !ipAddress.isEmpty()) ? ipAddress : request.getRemoteAddr();
    }
}

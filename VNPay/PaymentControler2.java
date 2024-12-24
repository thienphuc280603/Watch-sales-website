package com.poly.VNPay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin("*")
@RestController
@RequestMapping("/apipayment")
public class PaymentControler2 {
	  private long totalPrice;
	  @GetMapping("/calculateTotalPrice/{totalPrice}")
	    public ResponseEntity<?> calculateTotalPrice(@PathVariable long totalPrice) {
	        this.totalPrice = totalPrice;
	        
	        return ResponseEntity.ok(totalPrice);
	    }
	 
	@GetMapping("/createPayment")
	public ResponseEntity<?> createPayment() throws UnsupportedEncodingException {
		 	
	        String orderType = "other";
	        long amount = totalPrice*100;
	        String bankCode = "NCB";
	        
	        String vnp_TxnRef = Config.getRandomNumber(8);
	        String vnp_IpAddr = "127.0.0.1";

	        String vnp_TmnCode = Config.vnp_TmnCode;
	        
	        Map<String, String> vnp_Params = new HashMap<>();
	        vnp_Params.put("vnp_Version", Config.vnp_Version);
	        vnp_Params.put("vnp_Command", Config.vnp_Command);
	        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
	        vnp_Params.put("vnp_Amount", String.valueOf(amount));
	        vnp_Params.put("vnp_CurrCode", "VND");
	        
	        vnp_Params.put("vnp_BankCode", bankCode);
	        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
	        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
	        vnp_Params.put("vnp_OrderType", orderType);

	        
	        vnp_Params.put("vnp_Locale", "vn");
	        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
	        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

	        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	        String vnp_CreateDate = formatter.format(cld.getTime());
	        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
	        
	        cld.add(Calendar.MINUTE, 15);
	        String vnp_ExpireDate = formatter.format(cld.getTime());
	        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
	        
	        List fieldNames = new ArrayList(vnp_Params.keySet());
	        Collections.sort(fieldNames);
	        StringBuilder hashData = new StringBuilder();
	        StringBuilder query = new StringBuilder();
	        Iterator itr = fieldNames.iterator();
	        while (itr.hasNext()) {
	            String fieldName = (String) itr.next();
	            String fieldValue = (String) vnp_Params.get(fieldName);
	            if ((fieldValue != null) && (fieldValue.length() > 0)) {
	                //Build hash data
	                hashData.append(fieldName);
	                hashData.append('=');
	                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
	                //Build query
	                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
	                query.append('=');
	                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
	                if (itr.hasNext()) {
	                    query.append('&');
	                    hashData.append('&');
	                }
	            }
	        }
	        String queryUrl = query.toString();
	        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
	        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
	        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
	       
	        PaymentRestDTo paymentDTO = new PaymentRestDTo();
	        paymentDTO.setStatus("OK");
	        paymentDTO.setMessage("Successfully");
	        paymentDTO.setURl(paymentUrl);
	        System.out.println("createPayment called with amount: " + amount);
	        return ResponseEntity.status(HttpStatus.OK).body(paymentDTO);
	}
	@GetMapping("/paymentInfo")
	public ResponseEntity<?> transaction(@RequestParam(value = "vnp_Amount") String amount,
			@RequestParam(value = "vnp_BankCode") String bankcode,
			@RequestParam(value = "vnp_OrderInfo") String order,
			@RequestParam(value = "vnp_ResponseCode") String responsecode){
		TransactionDTO tran = new TransactionDTO();
		if(responsecode.equals("00")){			
			tran.setStatus("OK");
			tran.setMessage("Successfully");
			tran.setData("");
			
		}else {
			tran.setStatus("No");
			tran.setMessage("Faile");
			tran.setData("");
		}
		return ResponseEntity.status(HttpStatus.OK).body(tran);
	}
	
	
}

package com.mhdanh.mytemplate.service.implement;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mhdanh.mytemplate.viewmodel.RechargeCardResponseModel;

/**
 *
 * @author HieuNguyen
 */
@Service
@Transactional
public class BaokimSCard {

	private static final Logger logger = Logger.getLogger(BaokimSCard.class);
	
	//Merchant cáº§n cáº¥u hÃ¬nh cÃ¡c tham sá»‘ sau Ä‘Ã¢y
	private final String BAOKIM_CARD_API = "https://www.baokim.vn/the-cao/restFul/send";
    private final String HTTP_USERNAME = "merchant_16544";
    private final String HTTP_PASSWORD = "16544nQrMNVv71BSEJUIKWO68zagicG8C4P";
    private final String API_USERNAME = "myuiinfo";
    private final String API_PASSWORD = "myuiinfoMDHG75arfsDGjjd";
    private final String SECURE_CODE = "9c4d1ab9d6771b88";
    private final String MERCHANT_ID = "16544";
    
    private final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private String supplier;
    private String seri;
    private String pincode;
    private String transaction_id;

    public RechargeCardResponseModel rechargeCard(String supplier, String seri, String pincode) {
    	try {
    	RechargeCardResponseModel responseRechargeCard = new RechargeCardResponseModel();
		String[] rs = this.send(supplier, seri, pincode);
        String responseCode=rs[0];
		String responseBody=rs[1];
		//Xá»­ lÃ½ dá»¯ liá»‡u tráº£ vá»� dáº¡ng json
			JSONParser parser=new JSONParser();
			Object obj = parser.parse(responseBody);
			JSONObject jsonObject = (JSONObject) obj;
			if(!"200".equals(responseCode)){
				//save error state
				responseRechargeCard.setResponseCode(responseCode);
				responseRechargeCard.setTransactionId(String.valueOf(jsonObject.get("transaction_id")));
				responseRechargeCard.setErrorMessage(String.valueOf(jsonObject.get("errorMessage")));
			}else{
				//save success state
				responseRechargeCard.setResponseCode(responseCode);
				responseRechargeCard.setTransactionId(String.valueOf(jsonObject.get("transaction_id")));
				responseRechargeCard.setAmount(Integer.valueOf(String.valueOf(jsonObject.get("amount"))));
			}
			return responseRechargeCard;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("rechargeCard error",e);
			return null;
		}
    }
    
    private String[] send(String supplier, String seri, String pincode) {
    	try {
	        this.supplier = supplier;
	        this.seri = seri;
	        this.pincode = pincode;
	        Date date = new Date();
	        this.transaction_id = String.valueOf(date.getTime());
	
	        Map<String, String> mapA = new HashMap<String, String>();
	        mapA.put("merchant_id", this.MERCHANT_ID);
	        mapA.put("api_username", this.API_USERNAME);
	        mapA.put("api_password", this.API_PASSWORD);
	        mapA.put("transaction_id", this.transaction_id);
	        mapA.put("card_id", this.supplier);
	        mapA.put("pin_field", this.pincode);
	        mapA.put("seri_field", this.seri);
	        mapA.put("algo_mode", "hmac");
	
			//Sort map by key
			Map<String, String> arrayPost = new TreeMap<String, String>(mapA);
	        //Hash data post create data sign
	        String data_sign = hmacCreateDataSign(mapA);
	        mapA.put("data_sign", data_sign);
	        String data = this.createRequestUrl(mapA);
	        
	        //Post dá»¯ liá»‡u tháº»
			return this.doPost(data);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String[] rs={"500",e.getMessage()};
			logger.error("send error",e);
	    	return rs;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String[] rs={"500",e.getMessage()};
			logger.error("send error",e);
	    	return rs;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String[] rs={"500",e.getMessage()};
			logger.error("send error",e);
	    	return rs;
		}
    }

    private String[] doPost(String data) throws IOException {
        
        installAllTrustManager();
        System.setProperty("jsse.enableSNIExtension", "false");
        
		URL url = new URL(this.BAOKIM_CARD_API);
        HttpsURLConnection request = (HttpsURLConnection) url.openConnection();
        
        Authenticator.setDefault( new MyAuthenticator(this.HTTP_USERNAME,this.HTTP_PASSWORD) );
        request.setAllowUserInteraction(true);
        request.setConnectTimeout(3000);

        request.setUseCaches(false);
        request.setDoOutput(true);
        request.setDoInput(true);

        HttpsURLConnection.setFollowRedirects(true);
        request.setInstanceFollowRedirects(true);

        request.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        request.setRequestProperty("Content-length", String.valueOf(data.length()));
        request.setRequestMethod("POST");
        
        PrintWriter post = new PrintWriter(request.getOutputStream());
        post.print(data);
        post.close();
        
        InputStream is;
        String responseCode=String.valueOf(request.getResponseCode());
        if ("200".equals(responseCode)) {
            is = request.getInputStream();
        } else {
            is = request.getErrorStream();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            stringBuilder.append(line + "\n");
        }
        
        String responseBody=stringBuilder.toString();
        
        String[] rs={responseCode, responseBody};
        return rs;
    }

    /**
     * Raw String Data to generateHmacSHASignature
     *
     * @param params	
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    private String hmacCreateDataSign(Map<String, String> params) throws InvalidKeyException, NoSuchAlgorithmException {
        SortedSet<String> keys = new TreeSet<String>(params.keySet());
        String rawData = "";
        for (String key : keys) {
            String value = params.get(key);
            rawData = rawData + value;
        }

        return generateHmacSHASignature(rawData, this.SECURE_CODE);
    }

    /**
     * Create HmacSHAS1 Signature With Key
     *
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    private String generateHmacSHASignature(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        // get an hmac_sha1 key from the raw key bytes
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        // get an hmac_sha1 Mac instance and initialize with the signing key
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
        // base64-encode the hmac
        return toHexString(mac.doFinal(data.getBytes()));
    }

    /**
     * toHexString
     *
     * @param bytes
     * @return
     */
    private String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    /**
     *
     * @param map
     * @return
     */
    private String createRequestUrl(Map<String, String> map) {
        String url_params = "";
        for (Map.Entry entry : map.entrySet()) {
            if(url_params == "")
                url_params += entry.getKey() + "=" +  entry.getValue();
            else
                url_params += "&" + entry.getKey() + "=" +  entry.getValue();
        }
        return url_params;
    }

    /**
     *
     *
     */
    public void installAllTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection
                    .setDefaultHostnameVerifier(new HostnameVerifier() {
                        public boolean verify(String urlHostname,
                                javax.net.ssl.SSLSession _session) {
                            return true;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("installAllTrustManager error",e);
        }
    }
    
    

    /**
     * @param args the command line arguments
     * @throws  
     */
    public static void main(String[] args) {
        BaokimSCard bksc = new BaokimSCard();
        String[] rs = bksc.send("VIETEL", "92593748545", "0926969951884");
        
        String responseCode=rs[0];
		String responseBody=rs[1];
		
		//Xá»­ lÃ½ dá»¯ liá»‡u tráº£ vá»� dáº¡ng json
		try {
			JSONParser parser=new JSONParser();
			Object obj;
			obj = parser.parse(responseBody);
		
			JSONObject jsonObject = (JSONObject) obj;
			if(!responseCode.equals("200")){
				//TODO: xá»­ lÃ½ khi Báº£o kim tráº£ vá»� lá»—i
				System.out.println("response code: "+responseCode);
				System.out.println("transaction_id: "+jsonObject.get("transaction_id"));
				System.out.println("error message: "+jsonObject.get("errorMessage"));
			}else{
				//TODO: xá»­ lÃ½ khi Báº£o kim tráº£ vá»� thÃ nh cÃ´ng
				System.out.println("response code: "+responseCode);
				System.out.println("transaction_id: "+jsonObject.get("transaction_id"));
				System.out.println("amount: "+jsonObject.get("amount"));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}

class MyAuthenticator extends Authenticator {
	private String http_username;
	private String http_password;
	public MyAuthenticator(String http_username, String http_password){
		this.http_username=http_username;
		this.http_password=http_password;
	}
	/**
	 * Called when password authorization is needed.
	 * 
	 * @return The PasswordAuthentication collected from the user, or null if
	 *         none is provided.
	 */
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.http_username,
				this.http_password.toCharArray());
	}
}

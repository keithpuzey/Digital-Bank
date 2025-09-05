<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/service/VisaService.java
package io.digisic.bank.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
=======
package io.demo.bank.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.type.StringType;
import org.json.JSONObject;
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/service/VisaService.java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/service/VisaService.java
=======
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/service/VisaService.java
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/service/VisaService.java

import io.digisic.bank.util.Constants;

=======
import io.demo.bank.model.AtmLocation;
import io.demo.bank.util.Constants;
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/service/VisaService.java

@Service
@Transactional
public class VisaService {
	
	private static final Logger LOG = LoggerFactory.getLogger(VisaService.class);
	
<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/service/VisaService.java
	// Visa Service URL
	private static String apiBaseUrl;
	
	@Autowired
	private Environment environment;
	
	// Visa Service Default properties
	private static final String DEFAULT_VISA_PROTOCOL 	= "https";
	private static final String DEFAULT_VISA_HOSTNAME 	= "creditservices.io"; 	
	
	public List<String> directVisaPayment (String extAccount, String extAmount)	throws Exception {
		
		
		LOG.debug("Visa Service :-> Visa Account = " + extAccount );
		LOG.debug("VVisa Service :-> Visa Amount  = " + extAmount );
			
=======
	// ATM Location Service URL
	private static String apiBaseUrl;

	
	@Autowired
	private Environment environment;
		
	
	public List<AtmLocation> searchATMLocations (String zipcode, String visaamount)	throws Exception {
		
		LOG.debug("Visa Amount visa service  = " + visaamount );
		LOG.debug("Visa Account visa service = " + zipcode );	
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/service/VisaService.java
		
		if (VisaService.apiBaseUrl == null) {
			getConnectionProperties();
		}

		
<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/service/VisaService.java
		List<String> results = new ArrayList<String>();
=======
		List<AtmLocation> results = new ArrayList<AtmLocation>();
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/service/VisaService.java
		

		// Add query parameters for authentication credentials
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(VisaService.apiBaseUrl)
<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/service/VisaService.java
		                					.queryParam("idcode", extAccount)
											.queryParam("amount", extAmount);

		
=======
		                					.queryParam("idcode", zipcode)
											.queryParam("amount", visaamount);

		
//		LOG.debug("ATM Location Request: " + uriBuilder.toUriString());
		
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/service/VisaService.java
		// Create required headers
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.set("User-Agent", "PostmanRuntime/7.13.0");
		
<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/service/VisaService.java
       LOG.debug("Visa Service :-> Request = " + uriBuilder.toUriString());
=======
       
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/service/VisaService.java
		
        // Create the Request
     	HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);
     	
		
		RestTemplate restTemplate = new RestTemplate();
		
		try {
<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/service/VisaService.java
			
=======
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/service/VisaService.java
			ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.toUriString(), 
					 													  HttpMethod.GET, 
					 													  requestEntity, 
					 													  String.class);
			
<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/service/VisaService.java
			LOG.debug("Visa Service :-> Visa Response = " + responseEntity );
			
=======
			LOG.debug("Visa Response = " + responseEntity );
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/service/VisaService.java
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(responseEntity.getBody());
		    
		    
			JsonNode idCode = root.path("actionCode");
			JsonNode Approvalcode = root.path("approvalCode");
			JsonNode CAVVResult = root.path("cavvResultCode");


<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/service/VisaService.java
			LOG.debug("Visa Service :-> Action Code from visa  = " + idCode);
			LOG.debug("Visa Service :-> Approval Code from visa  = " + Approvalcode);
			LOG.debug("Visa Service :-> CAVVResult Code from visa  = " + CAVVResult);
			
			
			String approvalcode = "Approved";
			int approvalID = root.path("actionCode").asInt();
			
			LOG.debug("Action code integer value " + approvalID);			
			
			switch (Integer.valueOf(approvalID)) {
				case 0:
					approvalcode = "Approved";
					break;
				case 51:
					approvalcode = "Insufficient Funds";
					break;
				case 57:
					approvalcode = "Transaction Not Approved";
					break;
				case 03:
					approvalcode = "Denied Invalid Merchant";
					break;
				default:
					approvalcode = "Approved";	
			}
		    
			results.add("Approval Status = " + approvalcode);
			results.add("Approval Code = " + root.path("approvalCode").asText());
			results.add("CAVV Result Code = " + root.path("cavvResultCode").asText());
			results.add("VISA Response Code = " + root.path("responseCode").asText());
			results.add("VISA Transaction Identifier = " + root.path("transactionIdentifier").asText());
				
=======
			LOG.debug("Action Code from visa  = " + idCode);
			LOG.debug("Approval Code from visa  = " + Approvalcode);
			LOG.debug("CAVVResult Code from visa  = " + CAVVResult);
			
			


			String approvalcode = "Approved";
			
		
			final Integer approvalID = root.path("actionCode").asInt();
			LOG.debug("Action code integer value " + approvalID);			
			
		    
		    if (approvalID == 0) {
		    	approvalcode = "Approved";}
			else if (approvalID == 51) {
		    	approvalcode = "Insufficient Funds";}
			else if (approvalID == 57) {
		    	approvalcode = "Transaction Not Approved";}
			else if (approvalID == 03) {
		    	approvalcode = "Denied Invalid Merchant";}
	
						
					AtmLocation atm = new AtmLocation();
						
//						atm.setName("Action Code = " + root.path("actionCode").asText());
						atm.setName("Approval Status = " + approvalcode);
						atm.setDescription("Approval Code = " + root.path("approvalCode").asText());
						atm.setStreet("CAVV Result Code = " + root.path("cavvResultCode").asText());
						atm.setCity("VISA Response Code" + root.path("responseCode").asText());
						atm.setState("VISA Transaction Identifier = " + root.path("transactionIdentifier").asText());
						atm.setZipcode(root.path("postalCode").asText());
						atm.setCountry(root.path("country").asText());
						results.add(atm);

					
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/service/VisaService.java
		}
		catch (HttpStatusCodeException ex) {
			LOG.debug("VISA API Service: Unable to successfully get Visa Account");
			LOG.debug(ex.getMessage());
			LOG.debug(ex.getResponseBodyAsString());
			
			throw ex;
			
		}
		catch (ResourceAccessException ex) {
			LOG.error("VISA API Service: Unable to reach VISA API Service endpoint");
			LOG.error(ex.getMessage());
			
			throw ex;
			
		} catch (IOException ex) {
			LOG.error("VISA API Service: Unable to read response");
			LOG.error(ex.getMessage());
			
			throw ex;
		}
		
		return results;
	}
	
	
	
	/*
	 * Get VISA API Service details from application.properties
	 */
	private boolean getConnectionProperties () {
		
<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/service/VisaService.java
		String protocol = environment.getProperty(Constants.APP_VISA_PROTOCOL, DEFAULT_VISA_PROTOCOL);
		String host = environment.getProperty(Constants.APP_VISA_HOST, DEFAULT_VISA_HOSTNAME);
		String port = environment.getProperty(Constants.APP_VISA_PORT, "");
		
		VisaService.apiBaseUrl = protocol + "://" + host;
		
		// check port values to see if it needs to be added to URL
		if (port != null && !port.isEmpty()) {
			if (!port.equals("443") && !port.equals("80")) { 
				VisaService.apiBaseUrl += ":" + port;
			} else {
				// if the port is a default of 443 or 80, then only add the port if 
				// it has been specified without the associated default protocol for the port.
				if (port.equals("443") && protocol.equals("http")) {
					VisaService.apiBaseUrl += ":" + port;
				}
				if (port.equals("80") && protocol.equals("https")) {
					VisaService.apiBaseUrl += ":" + port;
				}
			}
		}
		
		VisaService.apiBaseUrl += Constants.APP_VISA_URI_API_BASE;

		LOG.debug("VISA API Service URL: " + VisaService.apiBaseUrl);
		
		// Make sure values were passed in for these properties
		if (protocol == null ||
			host == null) {
=======
		String protocol = environment.getProperty(Constants.APP_VISA_PROTOCOL);
		String host = environment.getProperty(Constants.APP_VISA_HOST);
		String port = environment.getProperty(Constants.APP_VISA_PORT);
		
		VisaService.apiBaseUrl 	= protocol + "://"
				 				 	+ host + ":"
				 				 	+ port
				 				 	+ Constants.APP_VISA_URI_API_BASE;
		

		
		// Make sure values were passed in for these properties
		if (protocol == null ||
			host == null ||
			port == null) {
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/service/VisaService.java
			
			LOG.error("VISA API Service: Connection properties are missing in the configuration.");
			
			return false;
		}
		
		// Check the URL properties to ensure a valid URL can be formed
		try {
			UriComponentsBuilder.fromHttpUrl(VisaService.apiBaseUrl);
			
			return true;
		}
		catch (IllegalArgumentException ex) {
			LOG.error("VISA API Service: Connection properties for protocol, host, and port are not correct in the configuration.");
<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/service/VisaService.java
			LOG.error("VISA API Service URL: " + VisaService.apiBaseUrl);
=======
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/service/VisaService.java
			LOG.error(ex.getMessage());
		}
		
		return false;
	}

}
<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/service/VisaService.java
=======

>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/service/VisaService.java

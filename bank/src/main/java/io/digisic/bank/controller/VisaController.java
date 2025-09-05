<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/controller/VisaController.java
package io.digisic.bank.controller;

import java.util.List;
=======
package io.demo.bank.controller;

import java.util.List;

>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/controller/VisaController.java
import javax.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/controller/VisaController.java
import io.digisic.bank.exception.RestServiceUnavailableException;
import io.digisic.bank.service.VisaService;
import io.digisic.bank.util.Constants;
import io.digisic.bank.util.Messages;
import io.digisic.bank.util.Patterns;

=======
import io.demo.bank.exception.RestServiceUnavailableException;
import io.demo.bank.model.AtmLocation;
import io.demo.bank.service.VisaService;
import io.demo.bank.util.Messages;
import io.demo.bank.util.Patterns;
import io.demo.bank.util.Constants;
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/controller/VisaController.java

@Validated
@RestController
public class VisaController extends CommonController {
	
	
	@Autowired
	private VisaService visaService;
	
	/*
	 * VISA API by account number
	 */
<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/controller/VisaController.java
	@GetMapping (Constants.URI_API_EXTERNAL_VISA)
	public ResponseEntity<?> directVisaPayment (@RequestParam @Pattern (regexp=Patterns.VISA_ACCOUNT, 
            											   				 message=Messages.VISA_ACCOUNT_FORMAT) String account, String amount){
		
		try {
			
			List<String> results = visaService.directVisaPayment(account, amount);
						
			return ResponseEntity.ok(results);
		}
		catch (Exception ex) {
			
			throw new RestServiceUnavailableException(Messages.VISA_SVC_UNAVAILABLE);
=======
	@GetMapping (Constants.URI_API_SEARCH_VISA)
	public ResponseEntity<?> searchATMLocations (@RequestParam @Pattern (regexp=Patterns.VISA_ACCOUNT, 
            											   				 message=Messages.VISA_SEARCH_FORMAT) String zipcode, String visaamount){
		
		try {
			
			List<AtmLocation> locations = visaService.searchATMLocations(zipcode, visaamount);
						
			return ResponseEntity.ok(locations);
		}
		catch (Exception ex) {
			
			throw new RestServiceUnavailableException(Messages.VISA_SEARCH_UNAVAILABLE);
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/controller/VisaController.java
			
		}

	}

}
<<<<<<< HEAD:bank/src/main/java/io/digisic/bank/controller/VisaController.java

=======
>>>>>>> 5dc1f9a06974b3a3823560ebb1f18caf8ef0cc65:src/main/java/io/demo/bank/controller/VisaController.java

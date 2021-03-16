package ca.mcgill.ecse321.autoRepair.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.autoRepair.dao.AssistantRepository;
import ca.mcgill.ecse321.autoRepair.dao.CarRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.OwnerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ProfileRepository;
import ca.mcgill.ecse321.autoRepair.model.Assistant;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.model.Profile;

	
@ExtendWith(MockitoExtension.class)
public class AssistantServiceTest {
	@Mock 
	private AssistantRepository assisRepo;
	@InjectMocks
	private AssistantService assisService;

	private static final String ASSISTANT_USERNAME ="TestAssistant";
	private static final String ASSISTANT_PASSWORD ="TestPassword2";
	
	
	@BeforeEach
	public void setMockOutput() {
	
	lenient().when(assisRepo.findAssistantByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
		if(invocation.getArgument(0).equals(ASSISTANT_USERNAME)) {
			Assistant assistant = new Assistant();
		    assistant.setUsername(ASSISTANT_USERNAME);
			assistant.setPassword(ASSISTANT_PASSWORD);
			return assistant;
		}
	else {
		return null;
	}
	
});
	Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
		return invocation.getArgument(0);
	};
	lenient().when(assisRepo.save(any(Assistant.class))).thenAnswer(returnParameterAsAnswer);
}

	@Test
	public void testCreateAssistant() {                        
	assertEquals(0, assisService.getAllAssistants().size());  
	String username = "nameTest";
	String password = "passwordTest1";
	Assistant assistant =null;
	try {
     assistant = assisService.createAssistant(username, password);
	}catch(IllegalArgumentException e) {
		fail();
	}
	assertNotNull(assistant);
	assertEquals(username, assistant.getUsername());
	assertEquals(password, assistant.getPassword());
}
	@Test
	public void testFindAssistant() {
	assertEquals(0, assisService.getAllAssistants().size());

	Assistant assis = null;
	try {
     assis = assisService.getAssistant(ASSISTANT_USERNAME);
	}catch(IllegalArgumentException e) {
		fail();
	}
	assertNotNull(assis);
	assertEquals(assis.getUsername(), ASSISTANT_USERNAME);
	assertEquals(assis.getPassword(), ASSISTANT_PASSWORD);
}	
	
	
	@Test
	public void testCreateAssistantErrorTakenUsername() {
	assertEquals(0, assisService.getAllAssistants().size());  
	String username = ASSISTANT_USERNAME;
	String password = "Password123";
	Assistant assistant = null;
	String error = "";
	try {
 assistant = assisService.createAssistant(username, password);
	}catch (IllegalArgumentException e) {
		error = e.getMessage();
	}
	assertNull(assistant);
	assertEquals("Username is already taken.",error);
	}
	
	
	
	@Test
	public void testCreateAssistantErrorBlankUsername() {
	assertEquals(0, assisService.getAllAssistants().size());  
	String username ="";
	String password = "Password123";
 	Assistant assistant = null;
	String error = "";
	try {
 assistant = assisService.createAssistant(username, password);
	}catch (IllegalArgumentException e) {
		error = e.getMessage();
	}
	assertNull(assistant);
	assertEquals("Username cannot be blank",error);
	}
	@Test
	public void testCreateAssistantErrorBlankPassword() {
	assertEquals(0, assisService.getAllAssistants().size());  
	String username ="newUsername1";
	String password = "";
	Assistant assistant = null;
	String error = "";
	try {
 assistant = assisService.createAssistant(username, password);
	}catch (IllegalArgumentException e) {
		error = e.getMessage();
	}
	assertNull(assistant);
	assertEquals("Password cannot be blank",error);
	}

	
	@Test
	public void testCreatAssistantWithInvalidPasswordLessThan8Chars() {
		String username ="nameTest";
		String invalidPassword = "Length1";
		Assistant assistant = null;
		String error = null;

		try {
		assistant = assisService.createAssistant(username,invalidPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(assistant);
		assertEquals("Password must have at least 8 characters",error);
	}
	
	
	
	@Test
	public void testCreateAssistantWithInvalidPasswordMoreThan20Chars() {
		String username ="nameTest";
		String invalidPassword = "ThisisaLengthmorethan20characters";
		Assistant assistant = null;
		String error = null;
		try {
			assistant = assisService.createAssistant(username,invalidPassword);
		} catch (IllegalArgumentException e) { 
			error = e.getMessage();
		}
		assertNull(assistant);
		assertEquals("Password must not have more than 20 characters",error);
	}
	@Test
	public void testCreateAssistantWithInvalidPasswordNoUpperCase() {
		String username ="nameTest";
		String invalidPassword = "noupper1";
		Assistant assistant = null;
		String error = null;
		try {
		 assistant = assisService.createAssistant(username,invalidPassword);
		} catch (IllegalArgumentException e) { 
			error = e.getMessage();
		}
		assertNull(assistant);
		assertEquals("Password must contain at least one uppercase character",error);
	}
	@Test
	public void testCreateAssistantWithInvalidPasswordNoLowerCase() {
		String username ="nameTest";
		String invalidPassword = "NOLOWER1";
		Assistant assistant = null;
		String error = null;
		try {
			assistant = assisService.createAssistant(username,invalidPassword);
		} catch (IllegalArgumentException e) {  
			error = e.getMessage();
		}
		assertNull(assistant);
		assertEquals("Password must contain at least one lowercase character",error);
	}
	@Test
	public void testCreateAssistantWithInvalidPasswordNoNumericalValue() {
		String username ="nameTest";
		String invalidPassword = "Nonumbers";
		Assistant assistant = null;
		String error = null;

		try {
			assistant = assisService.createAssistant(username,invalidPassword);
		} catch (IllegalArgumentException e) { 
			error = e.getMessage();
		}
		assertNull(assistant);
		assertEquals("Password must contain at least one numeric character",error);
	}
	
	@Test
	public void testUpdateAssistantUsername() {
	Assistant assistant = null;

	try {
		assistant = assisService.updateAssistant(ASSISTANT_USERNAME,"NewAssisName",ASSISTANT_PASSWORD);
	}catch(IllegalArgumentException e) {
		fail();
	}
	assertNotNull(assistant);
	assertEquals(ASSISTANT_PASSWORD,assistant.getPassword());
	assertEquals("NewAssisName",assistant.getUsername());
   }
	
	

	@Test
	public void testUpdateAssistantPassword() {
	Assistant assistant = null;

	try {
		assistant = assisService.updateAssistant(ASSISTANT_USERNAME,ASSISTANT_USERNAME,"newPassword123");
	}catch(IllegalArgumentException e) {
		fail();
	}
	assertNotNull(assistant);
	assertEquals(ASSISTANT_USERNAME,assistant.getUsername());
	assertEquals("newPassword123",assistant.getPassword());
   }
	
	@Test
	public void testUpdateSamePassword() {
		Assistant assistant = null;

	try {
		assistant = assisService.updateAssistant(ASSISTANT_USERNAME,ASSISTANT_USERNAME,ASSISTANT_PASSWORD);
	}catch(IllegalArgumentException e) {
	 	fail();
	}	
	assertNotNull(assistant);
	assertEquals(ASSISTANT_USERNAME,assistant.getUsername());
	assertEquals(ASSISTANT_PASSWORD,assistant.getPassword());
  }
	
	@Test
	public void testUpdateAssistantWithInvalidPasswordLessThan8Chars() {
		Assistant assistant =null;
		String invalidPassword ="Invali1";
		String error = "";

		try {
		assistant = assisService.updateAssistant(ASSISTANT_USERNAME,ASSISTANT_USERNAME,invalidPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(assistant);
		assertEquals("Password must have at least 8 characters",error);
	}
	@Test
	public void testUpdateAssistantWithInvalidPasswordMoreThan20Chars() {
		Assistant assistant =null;
		String invalidPassword ="invalidbecausemoreThan20charS";
		String error = "";

		try {
		assistant = assisService.updateAssistant(ASSISTANT_USERNAME,ASSISTANT_USERNAME,invalidPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(assistant);
		assertEquals("Password must not have more than 20 characters",error);
	}
	@Test
	public void testUpdateAssistantWithInvalidPasswordNoUpperCase() {
		Assistant assistant =null;
		String invalidPassword ="nouppercase1";
		String error = "";

		try {
		assistant = assisService.updateAssistant(ASSISTANT_USERNAME,ASSISTANT_USERNAME,invalidPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(assistant);
		assertEquals("Password must contain at least one uppercase character",error);
	}
	@Test
	public void testUpdateAssistantWithInvalidPasswordNoLowerCase() {
		Assistant assistant =null;
		String invalidPassword ="ALLCAPSINVALID1";
		String error = "";

		try {
		assistant = assisService.updateAssistant(ASSISTANT_USERNAME,ASSISTANT_USERNAME,invalidPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(assistant);
		assertEquals("Password must contain at least one lowercase character",error);
	}
	@Test
	public void testUpdateAssistantWithInvalidPasswordNoNumericalValue() {
		Assistant assistant =null;
		String invalidPassword ="noNumericals";
		String error = "";

		try {
		assistant = assisService.updateAssistant(ASSISTANT_USERNAME,ASSISTANT_USERNAME,invalidPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(assistant);
		assertEquals("Password must contain at least one numeric character",error);
	}	
	@Test
	public void testDeleteAssistant() {
		boolean assistant =false;
		String error = "";
		try {
     assistant = assisService.deleteAssistant(ASSISTANT_USERNAME);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
	}
		assertEquals(assistant,true);
	}
	@Test
	public void testDeleteAssistantError() {
		boolean assistant =false;
		String error = "";
		try {
     assistant = assisService.deleteAssistant("error");
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
	}
		assertEquals(assistant,false);
	}
}


	
	
	
	
	
	
	
	
	
	
	
	
	
	

  
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.autoRepair.dao.OwnerRepository;
import ca.mcgill.ecse321.autoRepair.model.Owner;


@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {
	@Mock 
	private OwnerRepository ownerRepo;
	@InjectMocks
	private OwnerService ownerService;
	
	private static final String OWNER_USERNAME ="TestOwner";
	private static final String OWNER_PASSWORD ="TestPassword1";
	
	
	@BeforeEach
	public void setMockOutput() {
			
		lenient().when(ownerRepo.findOwnerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
 		 	if(invocation.getArgument(0).equals(OWNER_USERNAME)) {
				Owner owner = new Owner();
			    owner.setUsername(OWNER_USERNAME);
		 		owner.setPassword(OWNER_PASSWORD);
				return owner;
			}
		else {
			return null;
		}
	});
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		}; 
		lenient().when(ownerRepo.save(any(Owner.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	
	
	@Test
	public void testCreateOwner() {
	assertEquals(0, ownerService.getAllOwners().size());  
	String username = "nameTest";
	String password = "passwordTest1";
	String authentificationCode ="1234";
	Owner owner = null;
	try {
     owner = ownerService.createOwner(username, password, authentificationCode);
	}catch(IllegalArgumentException e) {
		fail();
	}
	assertNotNull(owner);
	assertEquals(username, owner.getUsername());
	assertEquals(password, owner.getPassword());
}	
	
	@Test
	public void testFindOwner() {
	assertEquals(0, ownerService.getAllOwners().size());  

	Owner owner = null;
	try {
     owner = ownerService.getOwner(OWNER_USERNAME);
	}catch(IllegalArgumentException e) {
		fail();
	}
	assertNotNull(owner);
	assertEquals(owner.getUsername(), OWNER_USERNAME);
	assertEquals(owner.getPassword(), OWNER_PASSWORD);
}	
	
	@Test
	public void testCreateOwnerErrorWrongAuthentification() {
	assertEquals(0, ownerService.getAllOwners().size());  
	String username ="ownerTest2";
	String password = "Password123";
	String authentication="4567";
	Owner owner = null;
	String error = "";
	try {
 owner = ownerService.createOwner(username, password,authentication);
	}catch (IllegalArgumentException e) {
		error = e.getMessage();
	}
	assertNull(owner);
	assertEquals("wrong authentification code,please try again.",error);
	}
	
	
	@Test
	public void testCreateOwnerErrorBlankUsername() {
	assertEquals(0, ownerService.getAllOwners().size());  
	String username ="";
	String password = "Password123";
	String authentication="1234";
	Owner owner = null;
	String error = "";
	try {
 owner = ownerService.createOwner(username, password,authentication);
	}catch (IllegalArgumentException e) {
		error = e.getMessage();
	}
	assertNull(owner);
	assertEquals("Username cannot be blank",error);
	}
	@Test
	public void testCreateOwnerErrorBlankPassword() {
	assertEquals(0, ownerService.getAllOwners().size());  
	String username ="newUsername1";
	String password = "";
	String authentication="1234";
	Owner owner = null;
	String error = "";
	try {
		 owner = ownerService.createOwner(username, password,authentication);
	}catch (IllegalArgumentException e) {
		error = e.getMessage();
	}
	assertNull(owner);
	assertEquals("Password cannot be blank",error);
	}

	@Test
	public void testCreateOwnerWithInvalidPasswordLessThan8Chars() {
		String username ="nameTest";
		String invalidPassword = "Length1";
		String authentificationCode ="1234";
		Owner owner = null;
		String error = null;

		try {
		owner = ownerService.createOwner(username,invalidPassword,authentificationCode);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Password must have at least 8 characters",error);
	}
	@Test
	public void testCreateOwnerWithInvalidPasswordMoreThan20Chars() {
		String username ="nameTest";
		String invalidPassword = "ThisisaLengthmorethan20characters";
		String authentificationCode ="1234";
		Owner owner = null;
		String error = null;
		try {
			owner = ownerService.createOwner(username,invalidPassword,authentificationCode);
		} catch (IllegalArgumentException e) { 
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Password must not have more than 20 characters",error);
	}
	@Test
	public void testCreateOwnerWithInvalidPasswordNoUpperCase() {
		String username ="nameTest";
		String invalidPassword = "noupper1";
		String authentificationCode ="1234";
		Owner owner = null;
		String error = null;
		try {
			owner = ownerService.createOwner(username,invalidPassword,authentificationCode);
		} catch (IllegalArgumentException e) { 
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Password must contain at least one uppercase character",error);
	}
	@Test
	public void testCreateOwnerWithInvalidPasswordNoLowerCase() {
		String username ="nameTest";
		String invalidPassword = "NOLOWER1";
		String authentificationCode ="1234";
		Owner owner = null;
		String error = null;
		try {
			owner = ownerService.createOwner(username,invalidPassword,authentificationCode);
		} catch (IllegalArgumentException e) {  
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Password must contain at least one lowercase character",error);
	}
	@Test
	public void testCreateOwnerWithInvalidPasswordNoNumericalValue() {
		String username ="nameTest";
		String invalidPassword = "Nonumbers";
		String authentificationCode ="1234";
		Owner owner = null;
		String error = null;

		try {
			owner = ownerService.createOwner(username,invalidPassword,authentificationCode);
		} catch (IllegalArgumentException e) { 
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Password must contain at least one numeric character",error);
	}
	
	
	@Test
	public void testUpdateOwnerPassword() {
	Owner owner = null;

	try {
		owner = ownerService.updateOwner(OWNER_USERNAME,OWNER_USERNAME,"newPassword123");
	}catch(IllegalArgumentException e) {
		fail();
	}
	assertNotNull(owner);
	assertEquals(OWNER_USERNAME,owner.getUsername());
	assertEquals("newPassword123",owner.getPassword());
  }
	
	@Test
	public void testUpdateSamePassword() {
	Owner owner = null;

	try {
		owner = ownerService.updateOwner(OWNER_USERNAME,OWNER_USERNAME,OWNER_PASSWORD);
 	}catch(IllegalArgumentException e) {
	 	fail();
	}
	assertNotNull(owner);
	assertEquals(OWNER_USERNAME,owner.getUsername());
	assertEquals(OWNER_PASSWORD,owner.getPassword());
  }
	
	
	@Test
	public void testUpdateOwnerUsername() {
	Owner owner = null;

	try {
		owner = ownerService.updateOwner(OWNER_USERNAME,"newUsername","newPassword123");
 	}catch (IllegalArgumentException e) {
		fail();
	}
	assertEquals(OWNER_USERNAME,owner.getUsername()); 
  }
	
	@Test
	public void testUpdateOwnerWithInvalidPasswordLessThan8Chars() {
		Owner owner =null;
		String invalidPassword ="invalid";
		String error = "";

		try {
		owner = ownerService.updateOwner(OWNER_USERNAME,OWNER_USERNAME,invalidPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Password must have at least 8 characters",error);
	}
	@Test
	public void testUpdateOwnerWithInvalidPasswordMoreThan20Chars() {
		Owner owner =null;
		String invalidPassword ="invalidbecauseitisMorethan20";
		String error = "";

		try {
		owner = ownerService.updateOwner(OWNER_USERNAME,OWNER_USERNAME,invalidPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Password must not have more than 20 characters",error);
	}
	@Test
	public void testUpdateOwnerWithInvalidPasswordNoUpperCase() {
		Owner owner =null;
		String invalidPassword ="noouppercasepass2";
		String error = "";

		try {
		owner = ownerService.updateOwner(OWNER_USERNAME,OWNER_USERNAME,invalidPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Password must contain at least one uppercase character",error);
	}
	@Test
	public void testUpdateOwnerWithInvalidPasswordNoLowerCase() {
		Owner owner =null;
		String invalidPassword ="ALLCAPSNOLOWER1";
		String error = "";

		try {
		owner = ownerService.updateOwner(OWNER_USERNAME,OWNER_USERNAME,invalidPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Password must contain at least one lowercase character",error);
	}
	@Test
	public void testUpdateOwnerWithInvalidPasswordNoNumericalValue() {
		Owner owner =null;
		String invalidPassword ="nonumbersA";
		String error = "";

		try {
		owner = ownerService.updateOwner(OWNER_USERNAME,OWNER_USERNAME,invalidPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Password must contain at least one numeric character",error);
	}	
	

}

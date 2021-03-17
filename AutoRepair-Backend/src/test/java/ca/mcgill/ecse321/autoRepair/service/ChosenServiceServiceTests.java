package ca.mcgill.ecse321.autoRepair.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;

import ca.mcgill.ecse321.autoRepair.model.ChosenService;




@ExtendWith(MockitoExtension.class)
public class ChosenServiceServiceTests {

	@Mock
	private ChosenServiceRepository csRepo;

	@InjectMocks
	private ChosenServiceService csService;

	private static final String CSName = "TestName";
	private static final int theDuration = 5;
	private static final Double thePrice = 321.1;

	@BeforeEach
	public void setMockOutput() {
		lenient().when(csRepo.findChosenServiceByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if((invocation.getArgument(0).equals(CSName))) {

				ChosenService cs = new ChosenService();
				cs.setName(CSName);
				cs.setDuration(theDuration);
				cs.setPayment(thePrice);
				return cs;

			}else {
				return null;
			}
		});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(csRepo.save(any(ChosenService.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testCreateChosenService() { 
		assertEquals(0, csService.getAllChosenService().size()); 
		String namee = "Service1";
		int dur = 5; 
		Double price = thePrice;
		ChosenService cs = null;
		
		try {
			cs = csService.createChosenService(namee,dur,price);
		}catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(cs);
		assertEquals(namee, cs.getName());
		assertEquals(dur,cs.getDuration());
		assertEquals(price,cs.getPayment());
	}

	@Test
	public void testNullNameCreateChosenService() { 
		assertEquals(0, csService.getAllChosenService().size()); 
		String namee = null;
		int dur = 5;
		Double price = thePrice;
		ChosenService cs = null;
		String error = null;
		try {
			cs = csService.createChosenService(namee,dur,price);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(cs);
		assertEquals("Invalid name", error);
	}

	@Test
	public void testEmptyNameCreateChosenService() {
		assertEquals(0, csService.getAllChosenService().size()); 
		String namee = "";
		Double price = thePrice;
		int dur = 5;
		ChosenService cs = null;
		String error = null;
		try {
			cs = csService.createChosenService(namee,dur,price);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(cs);
		assertEquals("Invalid name", error);
	}

	@Test
	public void testSpacesNameCreateChosenService() {
		assertEquals(0, csService.getAllChosenService().size()); 
		String namee = "       ";
		int dur = 5;
		Double price = thePrice;
		ChosenService cs = null;
		String error = null;
		try {
			cs = csService.createChosenService(namee,dur,price);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(cs);
		assertEquals("Invalid name", error);
	}

	@Test
	public void testCreateChosenServiceNullPrice() {
		assertEquals(0, csService.getAllChosenService().size()); 
		String namee = "Service2";
		int dur = 5;
		Double price = null;
		ChosenService cs = null;
		String error = null;
		try {
			cs = csService.createChosenService(namee,dur,price);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(cs);
		assertEquals("Invalid Price", error);
	}
	
	@Test
	public void testCreateChosenServiceTakenName() { 
		assertEquals(0, csService.getAllChosenService().size()); 
		int dur = 7;
		Double price = thePrice;
		ChosenService cs = null;
		String error = null;
		try {
			cs = csService.createChosenService(CSName,dur,price);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(cs);
		assertEquals("Name is already taken",error);


	}

	@Test
	public void testNoDurationCreateChosenService() {
		assertEquals(0, csService.getAllChosenService().size()); 
		String namee = "Servicito";
		int dur = 0;
		Double price = thePrice;
		ChosenService cs = null;
		String error = null;
		try {
			cs = csService.createChosenService(namee,dur,price);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(cs);
		assertEquals("Invalid duration", error);
	}
	
	@Test
	public void testEditChosenService() {
		assertEquals(0, csService.getAllChosenService().size());
		Double price = 8.6;
		ChosenService cs = null;
		try {
			cs = csService.editChosenService(CSName,6,price);
		}catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(cs);
		assertEquals(CSName, cs.getName());
		assertEquals(6,cs.getDuration());
		assertEquals(8.6, cs.getPayment());
	}
	
	@Test
	public void testEditChosenServiceNullName() {
		assertEquals(0, csService.getAllChosenService().size()); 

		String namee = null;
		int dur = 9;
		Double price = 8.6;
		ChosenService cs = null;
		String error = null;
		try {
			cs = csService.editChosenService(namee,dur,price);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(cs);
		assertEquals("Invalid name",error);
	}
	@Test
	public void testEditChosenServiceEmptyName() {
		assertEquals(0, csService.getAllChosenService().size()); 

		String namee = "";
		int dur = 9;
		Double price = 8.6;
		ChosenService cs = null;
		String error = null;
		try {
			cs = csService.editChosenService(namee,dur,price);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(cs);
		assertEquals("Invalid name",error);
	}
	@Test
	public void testEditChosenServiceSpacesName() {
		assertEquals(0, csService.getAllChosenService().size()); 

		String namee = "";
		int dur = 9;
		Double price = 8.6;
		ChosenService cs = null;
		String error = null;
		try {
			cs = csService.editChosenService(namee,dur,price);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(cs);
		assertEquals("Invalid name",error);
	}
	
	@Test
	public void testEditChosenServiceZeroDuration() {
		assertEquals(0, csService.getAllChosenService().size()); 

		String namee = CSName;
		int dur = 0;
		Double price = 8.6;
		ChosenService cs = null;
		String error = null;
		try {
			cs = csService.editChosenService(namee,dur,price);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(cs);
		assertEquals("Invalid duration",error);
	}
	
	@Test
	public void testEditChosenServiceNullPrice() {
		assertEquals(0, csService.getAllChosenService().size()); 

		String namee = CSName;
		int dur = 9;
		Double price = null;
		ChosenService cs = null;
		String error = null;
		try {
			cs = csService.editChosenService(namee,dur,price);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(cs);
		assertEquals("Invalid Price",error);
	}
	
	@Test
	public void testEditChosenServiceNotThere() {
		assertEquals(0, csService.getAllChosenService().size()); 

		String namee = "hello";
		int dur = 9;
		Double price = 8.6;
		ChosenService cs = null;
		String error = null;
		try {
			cs = csService.editChosenService(namee,dur,price);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(cs);
		assertEquals("Chosen Service invalid",error);
	}
	
	@Test
	public void testDeleteChosenService() {
		assertEquals(0, csService.getAllChosenService().size());

		String namee = CSName;
		boolean cs = false;
		try {
			cs = csService.deleteChosenService(namee);
		}catch (IllegalArgumentException e) {
			fail();
		}
		assertTrue(cs);
	}

	@Test
	public void testDeleteChosenServiceNotThere() {
		assertEquals(0, csService.getAllChosenService().size()); 

		String namee = "hi";
		boolean cs = false;
		String error = null;
		try {
			cs = csService.deleteChosenService(namee);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertFalse(cs);
		assertEquals("Chosen Service invalid",error);
	}
	
	@Test
	public void testDeleteChosenServiceNullName() {
		assertEquals(0, csService.getAllChosenService().size()); 

		String namee = null;
		boolean cs = false;
		String error = null;
		try {
			cs = csService.deleteChosenService(namee);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertFalse(cs);
		assertEquals("Invalid name",error);
	}
	
	@Test
	public void testDeleteChosenServiceEmptyName() {
		assertEquals(0, csService.getAllChosenService().size()); 

		String namee = "";
		boolean cs = false;
		String error = null;
		try {
			cs = csService.deleteChosenService(namee);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertFalse(cs);
		assertEquals("Invalid name",error);
	}
	
	@Test
	public void testDeleteChosenServiceSpacesName() {
		assertEquals(0, csService.getAllChosenService().size()); 

		String namee = "  ";
		boolean cs = false;
		String error = null;
		try {
			cs = csService.deleteChosenService(namee);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertFalse(cs);
		assertEquals("Invalid name",error);
	}

}
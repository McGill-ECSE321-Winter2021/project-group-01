package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import ca.mcgill.ecse321.autoRepair.model.AutoRepairShopSytem;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAutoRepairPersistence {
    AutoRepairShopSytem system = new AutoRepairShopSytem("grp01");


    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void clearDatabase() {
        profileRepository.deleteAll();
        ownerRepository.deleteAll();
        reminderRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadOwner() {
        String name = "TestOwner";
        // First example for object save/load
        Owner owner = new Owner(name,"12345", system) );
        // First example for attribute save/load
       ownerRepository.save(owner);

        owner= null;

        owner = ownerRepository.findOwnerByName(name);
        assertNotNull(owner);
        assertEquals(name, owner.getUsername());
    }

    @Test
    public void testPersistAndLoadReminder() {
        String name = "ECSE321 Tutorial";
        Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
        Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
        Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
        Event event = new Event();
        event.setName(name);
        event.setDate(date);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        eventRepository.save(event);

        event = null;

        event = eventRepository.findEventByName(name);

        assertNotNull(event);
        assertEquals(name, event.getName());
        assertEquals(date, event.getDate());
        assertEquals(startTime, event.getStartTime());
        assertEquals(endTime, event.getEndTime());
    }

    @Test
    public void testPersistAndLoadProfile() {
        Customer customer = new Customer("TestCustomer", "12345",0,0, null, system);
        Profile testProfile = new Profile("profileId", "Test", "Profile", "Test Address", "Test zip", "4388661234",
                "Test email",customer);
        customerRepository.save(customer);
        profileRepository.save(testProfile);
        String profileId = "profileId";
        testProfile = null;

        testProfile = profileRepository.findByCustomer(customer);
        assertNotNull(testProfile);
        assertEquals(profileId, testProfile.getId());
        assertEquals(customer.getUsername(), testProfile.getCustomer().getUsername());
    }

}
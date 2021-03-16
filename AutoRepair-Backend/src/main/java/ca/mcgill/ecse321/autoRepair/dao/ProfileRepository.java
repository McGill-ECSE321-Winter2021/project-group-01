package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Profile;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long>{

	Profile findByEmail(String email);
}
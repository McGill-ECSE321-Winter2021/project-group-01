package ca.mcgill.ecse321.autoRepair.dao;

public class OwnerRepository {

<<<<<<< Updated upstream
}
=======
import ca.mcgill.ecse321.autoRepair.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, String>{

    Owner findOwnerByName(String name);

}
>>>>>>> Stashed changes


package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autoRepair.model.BookableService;

public interface BookableServiceRepository extends CrudRepository<BookableService,String> {

    BookableService findBookableServiceByName(String name);
}

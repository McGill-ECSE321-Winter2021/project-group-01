package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChosenServiceService {

    @Autowired
    ChosenServiceRepository chosenServiceRepository;

    @Transactional
    public ChosenService createChosenService(String name, int duration) {
        ChosenService cService = new ChosenService();
        cService.setName(name);
        cService.setDuration(duration);
        chosenServiceRepository.save(cService);
        return cService;
    }

    @Transactional
    public ChosenService getChosenService(String name) {
        return chosenServiceRepository.findChosenServiceByName(name);
    }

    @Transactional
    public List<ChosenService> getAllChosenService(){
        return toList(chosenServiceRepository.findAll());
    }

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;

    }
}
package ca.mcgill.ecse321.autoRepair.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import ca.mcgill.ecse321.autoRepair.dao.BusinessRepository;

public class BusinessController {
	
	@Autowired
	BusinessRepository businessRepository;

	@PutMapping("/setUpBusinessInfo/{forumID}")
	public ResponseEntity<?> unsubscribeFromForum(@PathVariable long forumID, @RequestHeader String token) {
		
		
		
		
		User user = userRepository.findUserByApiToken(token);
		Optional<Forum> forum = forumRepository.findById(forumID);
		if (user != null && forum.isPresent()) {
			ForumDTO newForum = forumService.unsubscribeFrom(forumID, user.getId());
			return new ResponseEntity<>(newForum, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}

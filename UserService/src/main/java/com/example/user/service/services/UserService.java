package com.example.user.service.services;

import com.example.user.service.entities.Hotel;
import com.example.user.service.entities.Rating;
import com.example.user.service.entities.User;
import com.example.user.service.exceptions.ResourceNotFoundException;
import com.example.user.service.externalServices.HotelService;
import com.example.user.service.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private HotelService hotelService;

    public User saveUser(User user){
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getUser(String userID){
        //Get User from database with the help of user repository
        User user = userRepository.findById(userID).orElseThrow(()-> new ResourceNotFoundException("User with given id is not present: " + userID));
        //Fetch ratings of the above user from Rating Service
        //http://localhost:8083/ratings/users/07bdcca6-2521-4d15-a231-4b170f0821af

        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+user.getUserId(), Rating[].class);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        logger.info("{}",ratings);

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //Api call to hotel service to get the hotel
            //http://localhost:8082/hotels/ab4b29ad-9c9b-4ace-8e73-c4be09e4766b

            //By using RestTemplate communication
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(),Hotel.class);
            //By using feignClient
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
//            logger.info("response status code:{} ", forEntity.getStatusCode());
            //set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }
}

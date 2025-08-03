package com.example.rating.services;

import com.example.rating.entities.Rating;
import com.example.rating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating create(Rating rating) {
        return ratingRepository.save(rating);
    }

    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> getRatingByUserId(String userId) {
        return ratingRepository.findByUserId(userId);
    }

    public List<Rating> getRatingByHotelId(String hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }



}

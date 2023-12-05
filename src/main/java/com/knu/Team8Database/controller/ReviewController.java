package com.knu.Team8Database.controller;

import com.knu.Team8Database.dto.ReviewDTO;
import com.knu.Team8Database.dto.ReviewReq;
import com.knu.Team8Database.entity.Admin;
import com.knu.Team8Database.entity.Detail_view;
import com.knu.Team8Database.entity.Review;
import com.knu.Team8Database.entity.Users;
import com.knu.Team8Database.repository.ReviewRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepository reviewRepository;

    @PostMapping("/submitReview")
    public String addReview(
            @RequestParam String reviewId,
            @RequestParam String userId,
            @RequestParam String adminId,
            @RequestParam String medicineId,
            @RequestParam Double reviewRating,
            @RequestParam String reviewComments,
            Model model
    ) {
//        Users user = UserRepository.findByUserId(userId);
//        Detail_view medicine = MedicineRepository.findByMedicineId(medicineId);
//        Admin admin = AdminRepository.findByAdminId(adminId);

        // Create a Review entity using Lombok Builder
        Review review = Review.builder()
                .reviewId(reviewId)
                .usersId(user)
                .medicineId(medicine)
                .adminId(admin)
                .reviewRating(reviewRating)
                .reviewDate(new Date())
                .reviewComments(reviewComments)
                .build();

        // Save the review to the database
        reviewRepository.save(review);

        // Redirect to the appropriate page
        return "redirect:/reviews/list";
    }

    @GetMapping("/review")
    public String showALLReview(Model model) {
        List<ReviewDTO> reviewDTOList = reviewRepository.findAllReview();
        List<Map<String, String>> reviewList = new Vector<>();
        for (ReviewDTO reviewDTO : reviewDTOList) {
            Map<String, String> review = new HashMap<>();
            review.put("medicineId", reviewDTO.getMedicineId());
            review.put("userName", reviewDTO.getUsersName());
            review.put("medicineName", reviewDTO.getMedicineName());
            review.put("rating", reviewDTO.getReviewRating());
            review.put("comments", reviewDTO.getReviewComments());
            review.put("date", reviewDTO.getReviewDate());

            reviewList.add(review);
        }

        // Print the contents of the reviewList to the console
        System.out.println("Review List Contents:");
        for (Map<String, String> review : reviewList) {
            System.out.println("Medicine ID: " + review.get("medicineId"));
            System.out.println("User Name: " + review.get("userName"));
            System.out.println("Medicine Name: " + review.get("medicineName"));
            System.out.println("Rating: " + review.get("rating"));
            System.out.println("Comments: " + review.get("comments"));
            System.out.println("Date: " + review.get("date"));
            System.out.println("-----------------------");

        }

        model.addAttribute("reviewList", reviewList);
        return "review_list";
    }





}
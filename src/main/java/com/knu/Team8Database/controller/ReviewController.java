package com.knu.Team8Database.controller;

import com.knu.Team8Database.dto.ReviewDTO;

import com.knu.Team8Database.entity.*;
import com.knu.Team8Database.repository.AdminRepository;
import com.knu.Team8Database.repository.MedicineRepository;
import com.knu.Team8Database.repository.ReviewRepository;
import com.knu.Team8Database.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final MedicineRepository medicineRepository;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;


    @GetMapping("/review")
    public String showALLReview(@RequestParam(value="page", defaultValue = "1") int page, Model model) {
        List<ReviewDTO> reviewDTOList = reviewRepository.findAllReview();
        List<Map<String, String>> reviewList = new Vector<>();
        for (int i = 0; i < 10; i++) {
            ReviewDTO reviewDTO = reviewDTOList.get(i + 10*(page-1));
            Map<String, String> review = new HashMap<>();
            review.put("medicineId", reviewDTO.getMedicineId());
            review.put("userName", reviewDTO.getUsersName());
            review.put("medicineName", reviewDTO.getMedicineName());
            review.put("rating", reviewDTO.getReviewRating());
            review.put("comments", reviewDTO.getReviewComments());
            review.put("date", reviewDTO.getReviewDate());

            reviewList.add(review);
        }


        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reviewDTOList.size() / 10 + 1);
        model.addAttribute("reviewList", reviewList);
        return "review_list";
    }

    @PostMapping("/review")
    public String saveReview(
            Double rating, String comments, String medicineId, Model model
    ) {
        String generateReviewId;
        String newReviewId;

        do {
            generateReviewId = generateRandomId();
            newReviewId = reviewRepository.findReviewID(generateReviewId);
        } while (newReviewId == null);


            List<String> userList = userRepository.getUsersId();
            Collections.shuffle(userList);
            Users user = userRepository.findByUsersId(userList.get(0));
            Detail_view medicine = medicineRepository.findByMedicineId(medicineId);
//            Admin admin = adminRepository.findByAdminId("xsiI32Xo");
            List<String> adminList = adminRepository.getAdminId();
            Collections.shuffle(adminList);
            Admin admin = adminRepository.findByAdminId(adminList.get(0));

        Review review = Review.builder()
                .reviewId(newReviewId)
                .usersId(user)
                .medicineId(medicine)
                .adminId(admin)
                .reviewRating(rating)
                .reviewDate(new Date())
                .reviewComments(comments)
                .build();

        reviewRepository.save(review);
        return "redirect:/search?param=" + medicineId;
    }


    private String generateRandomId() {
        int randomLength = new Random().nextInt(6) + 1; // 1에서 6 사이의 랜덤한 길이
        StringBuilder randomId = new StringBuilder();

        for (int i = 0; i < randomLength; i++) {
            randomId.append(new Random().nextInt(10)); // 0에서 9 사이의 랜덤한 숫자
        }

        return randomId.toString();
    }
}








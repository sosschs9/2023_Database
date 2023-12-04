package com.knu.Team8Database.controller;

import com.knu.Team8Database.dto.ReviewDTO;
import com.knu.Team8Database.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepository reviewRepository;

    @GetMapping("/review")
    public String showALLReview(Model model) {
        List<ReviewDTO> reviewDTOList = reviewRepository.findAllReview();
        Map<String, Map<String, Object>> reviewMap = new HashMap<>();

        for (ReviewDTO review : reviewDTOList) {
            String medicineId = review.getMedicineId();
            String medicineName = review.getMedicineName();
            String userName = review.getUsersName();
            String rating = review.getReviewRating();
            String comment = review.getReviewComments();

            // 이미 해당 medicineId가 맵에 존재하는 경우
            if (reviewMap.containsKey(medicineId)) {
                // 해당 medicineId의 리스트를 가져와서 리뷰 정보를 추가
                Map<String, Object> innerMap = reviewMap.get(medicineId);
                ((List<String>) innerMap.get("UserName")).add(userName);
                ((List<String>) innerMap.get("Rating")).add(rating);
                ((List<String>) innerMap.get("Comments")).add(comment);
            } else {
                // 해당 medicineId가 맵에 존재하지 않는 경우
                Map<String, Object> innerMap = new HashMap<>();
                List<String> names = new Vector();
                List<String> ratings = new Vector();
                List<String> comments = new Vector();
                names.add(userName);
                ratings.add(rating);
                comments.add(comment);

                innerMap.put("MedicineName", medicineName);
                innerMap.put("UserName", names);
                innerMap.put("Rating", ratings);
                innerMap.put("Comments", comments);
                // 새로운 medicineId로 리스트를 맵에 추가
                reviewMap.put(medicineId, innerMap);
            }
        }



        // 모델에 리뷰 맵 추가
        model.addAttribute("reviewMap", reviewMap);
        return "review_list";
    }



}



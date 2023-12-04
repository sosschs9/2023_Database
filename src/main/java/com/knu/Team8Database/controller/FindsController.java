package com.knu.Team8Database.controller;

import com.knu.Team8Database.dto.Detail_viewDTO;
import com.knu.Team8Database.dto.FindsDTO;
import com.knu.Team8Database.dto.ReviewDTO;
import com.knu.Team8Database.repository.FindsRepository;
import com.knu.Team8Database.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

@Controller
@RequiredArgsConstructor
public class FindsController {
    private final FindsRepository findsRepository;

    @GetMapping("/finds")
    public String showranking(Model model){
        List<FindsDTO> findsDTOList = findsRepository.findByTop5();
        Map<String, Map<String, Object>> findsMap = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            FindsDTO finds = findsDTOList.get(i);
            String findCount = finds.getFindCount();
            System.out.println(findCount);
            String medicineId = finds.getMedicineID();
            String medicineName = finds.getMedicineName();
            System.out.println(medicineName);

            Map<String, Object> innerMap = new HashMap<>();
            innerMap.put("FindCount", findCount);
            innerMap.put("MedicineName", medicineName);
            findsMap.put(medicineId, innerMap);
        }
        model.addAttribute("findsMap", findsMap);
        return "retrieve_ranking";
    }
}

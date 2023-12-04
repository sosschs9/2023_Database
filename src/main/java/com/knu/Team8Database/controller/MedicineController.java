package com.knu.Team8Database.controller;

import com.knu.Team8Database.dto.Detail_viewDTO;
import com.knu.Team8Database.dto.MedicineReq;
import com.knu.Team8Database.dto.ReviewDTO;
import com.knu.Team8Database.repository.MedicineRepository;
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
public class MedicineController {

    private final MedicineRepository medicineRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping("/search")
    public String searchDetail(@RequestParam("param") String medicineId, Model model) {
        List<ReviewDTO> reviewDTOList = reviewRepository.findMedicineReview(medicineId);
        Map<String, Map<String, Object>> reviewMap = new HashMap<>();

        for (ReviewDTO review : reviewDTOList) {
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

        List<Detail_viewDTO> medicineDetail = medicineRepository.find_detail(medicineId);
        Map<String, Map<String, Object>> itemMap = new HashMap<>();
        if (medicineDetail == null) System.out.println("빈 리스트임.");
        else {
            for (int i = 0; i < medicineDetail.size(); i++) {
                Detail_viewDTO medicine = medicineDetail.get(i);
                String medicineName = medicine.getMedicineName();
                String medicineCapacity = medicine.getMedicineCapacity();
                String medicinePrice = medicine.getMedicinePrice();
                String medicineManufactureDate = medicine.getMedicineManufactureDate();
                String symtomName = medicine.getSymtomName();
                String symtomField = medicine.getSymtomField();
                String companyName = medicine.getCompanyName();
                String companyPhoneNumber = medicine.getCompanyPhoneNumber();
                String companyWebsite = medicine.getCompanyWebsite();
                String componentName = medicine.getComponentName();
                String componentSideEffect = medicine.getComponentSideEffects();

                if (itemMap.containsKey(medicineId)) {
                    Map<String, Object> innerMap = itemMap.get(medicineId);
                    ((List<String>) innerMap.get("symtoms")).add(symtomName);
                    ((List<String>) innerMap.get("symtomFields")).add(symtomField);
                    ((List<String>) innerMap.get("components")).add(componentName);
                    ((List<String>) innerMap.get("componentSideEffects")).add(componentSideEffect);
                } else {
                    Map<String, Object> innerMap = new HashMap<>();
                    List<String> symtoms = new Vector();
                    List<String> symtomFields = new Vector();
                    List<String> components = new Vector();
                    List<String> componentSideEffects = new Vector();
                    symtoms.add(symtomName);
                    symtomFields.add(symtomField);
                    components.add(componentName);
                    componentSideEffects.add(componentSideEffect);
                    innerMap.put("symtoms", symtoms);
                    innerMap.put("symtomFields", symtomFields);
                    innerMap.put("components", components);
                    innerMap.put("componentSideEffects", componentSideEffects);
                    innerMap.put("medicineName", medicineName);
                    innerMap.put("medicineCapacity", medicineCapacity);
                    innerMap.put("medicinePrice", medicinePrice);
                    innerMap.put("medicineManufactureDate", medicineManufactureDate);
                    innerMap.put("companyName", companyName);
                    innerMap.put("companyPhoneNumber", companyPhoneNumber);
                    innerMap.put("companyWebsite", companyWebsite);
                    itemMap.put(medicineId, innerMap);
                }
            }
        }
        model.addAttribute("responseData", itemMap);

        return "detail";
    }

    @PostMapping("/search")
    public String searchMedicine(MedicineReq medicineReq, Model model) {
        if (medicineReq.getPrice().equals("")) medicineReq.setPrice("0");

        List<Detail_viewDTO> medicineList = medicineRepository.find_simple(
                medicineReq.getMedicine(),medicineReq.getComponent(),medicineReq.getSymptom(),
                medicineReq.getCompany(),Integer.parseInt(medicineReq.getPrice()), medicineReq.getField());

        Map<String,Map<String, Object>> itemMap = new HashMap<>();
        for(int i = 0; i< medicineList.size(); i++) {
            Detail_viewDTO medicines = medicineList.get(i);

            String medicinesId = medicines.getMedicineId();
            String medicinesName =  medicines.getMedicineName();
            String medicinesCapacity = medicines.getMedicineCapacity();
            String symptomsName = medicines.getSymtomName();
            String companysName = medicines.getCompanyName();

            if(itemMap.containsKey(medicinesId)){
                Map<String, Object> innerMap = itemMap.get(medicinesId);
                ((List<String>) innerMap.get("symptoms")).add(symptomsName);
            }
            else{
                Map<String, Object> innerMap = new HashMap<>();
                List<String> symptoms = new Vector();
                symptoms.add(symptomsName);
                innerMap.put("symptoms", symptoms);
                innerMap.put("medicineName", medicinesName);
                innerMap.put("medicineCapacity", medicinesCapacity);
                innerMap.put("companyName", companysName);
                itemMap.put(medicinesId, innerMap);
            }
        }

        model.addAttribute("responseData", itemMap);
        return "retrieve_result";
    }
}

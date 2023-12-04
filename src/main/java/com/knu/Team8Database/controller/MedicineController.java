package com.knu.Team8Database.controller;

import com.knu.Team8Database.dto.Detail_viewDTO;
import com.knu.Team8Database.dto.MedicineReq;
import com.knu.Team8Database.repository.MedicineRepository;
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

    @GetMapping("/search")
    public String searchDetail(@RequestParam("param") String medicineId, Model model) {
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

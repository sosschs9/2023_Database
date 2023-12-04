package com.knu.Team8Database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="detail_view")
public class Detail_view {

    @Id
    @Column(name = "medicine_id", nullable = false, length = 5)
    private String medicineId;

    @Column(name = "medicine_name", nullable = false,  columnDefinition = "NVARCHAR2(120)")
    private String medicineName;

    @Column(name = "medicine_capacity", columnDefinition = "NVARCHAR2(30)")
    private String medicineCapacity;

    @Column(name = "medicine_price", columnDefinition = "NUMBER")
    private Integer medicinePrice;

    @Column(name = "medicine_manufacture_date", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date medicineManufactureDate;

    @Column(name = "company_name", nullable = false, columnDefinition = "NVARCHAR2(15)")
    private String companyName;

    @Column(name = "company_phone_number", columnDefinition = "VARCHAR2(15)")
    private String companyPhoneNumber;

    @Column(name = "company_website", columnDefinition = "NVARCHAR2(50)")
    private String companyWebsite;

    @Column(name = "symtom_field", columnDefinition = "NVARCHAR2(15)")
    private String symtomField;

    @Column(name = "symtom_name", nullable = false, columnDefinition = "NVARCHAR2(15)")
    private String symtomName;

    @Column(name = "component_name", nullable = false, columnDefinition = "NVARCHAR2(15)")
    private String componentName;

    @Column(name = "component_side_effects", columnDefinition = "NVARCHAR2(50)")
    private String componentSideEffects;

    @OneToMany(mappedBy = "medicineId")
    private List<Review> reviews;

    @OneToMany(mappedBy = "medicineId")
    private List<Finds> finds;
    
}
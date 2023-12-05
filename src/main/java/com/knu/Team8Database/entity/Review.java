package com.knu.Team8Database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.thymeleaf.expression.Strings;

import java.util.Date;
import java.util.Random;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "review")
public class Review {
    @Id
    @Column(name = "review_id", nullable = false, length = 6)
    private String reviewId;




    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users usersId;

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Detail_view medicineId;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin adminId;

    @Column(name = "rating", nullable = false)
    private Double reviewRating;

    @Column(name = "review_date")
    @Temporal(TemporalType.DATE)
    private Date reviewDate;

    @Column(name = "comments", columnDefinition = "NVARCHAR2(50)")
    private String reviewComments;

}
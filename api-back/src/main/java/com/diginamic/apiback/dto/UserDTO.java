package com.diginamic.apiback.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.diginamic.apiback.enums.Role;
import com.diginamic.apiback.models.Absence;
import com.diginamic.apiback.models.Service;
import com.diginamic.apiback.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "manager")
    private List<User> users = new ArrayList<>();

    @ManyToOne
    private User manager;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Absence> absences = new ArrayList<>();

    private String firstName;

    private String lastName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Float rttEmployee;

    private Float unpaidLeave;

    private Float paidLeave;

    public UserDTO() {
    }

    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", users=" + users + ", manager=" + manager + ", service=" + service
                + ", absences=" + absences + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", role=" + role + ", rttEmployee=" + rttEmployee + ", unpaidLeave=" + unpaidLeave
                + ", paidLeave=" + paidLeave + "]";
    }

}
package com.trustpoint.cases.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "notes")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(value = {"_case"}, ignoreUnknown = true)
public class Note extends Model {
    @Id
    @GeneratedValue
    private UUID id;

    @NotEmpty
    @Column(columnDefinition="TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name = "caseId", nullable = false)
    @ToString.Exclude
    private Case _case;

    @NotEmpty
    @Email
    private String enteredBy;

    private String relatedActionType = "";

    private String relatedActionID = "";
}

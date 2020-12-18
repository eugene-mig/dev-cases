package com.trustpoint.cases.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Note extends Model {
    @Id
    @GeneratedValue
    private UUID id;

    @NotEmpty
    @Column(columnDefinition="TEXT")
    private String body;

    @NotNull
    private UUID caseID;

    @NotEmpty
    @Email
    private String enteredBy;

    private String relatedActionType = "";

    private String relatedActionID = "";
}

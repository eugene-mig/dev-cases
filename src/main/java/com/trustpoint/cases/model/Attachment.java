package com.trustpoint.cases.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "attachments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"_case"}, ignoreUnknown = true)
public class Attachment extends Model {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "caseId", nullable = false)
    @ToString.Exclude
    private Case _case;

    @Column(columnDefinition = "text", name = "description")
    private String desc = "";

    private String attachedBy = "";

    @NotNull
    private File file;
}

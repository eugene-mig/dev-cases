package com.trustpoint.cases.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity(name="Attachment")
@Table(name = "attachments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment extends Model {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private UUID caseID;

    @Column(columnDefinition = "TEXT", name = "description")
    private String desc = "";

    private String attachedBy = "";

    @NotNull
    private File file;
}

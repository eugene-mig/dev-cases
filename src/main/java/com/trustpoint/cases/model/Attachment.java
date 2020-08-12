package com.trustpoint.cases.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity(name="Attachment")
@Table(name = "attachments")
public class Attachment extends Model {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private UUID caseID;

    @Column(columnDefinition = "TEXT")
    private String description = "";

    private String attachedBy = "";

    @NotNull
    private File file;

    //    private List<Note> notes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public String getAttachedBy() {
        return attachedBy;
    }

    public void setAttachedBy(String attachedBy) {
        this.attachedBy = attachedBy;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public UUID getCaseID() {
        return caseID;
    }

    public void setCaseID(UUID caseID) {
        this.caseID = caseID;
    }
}

package com.trustpoint.cases.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity(name="Attachment")
@Table(name = "attachments")
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

package com.trustpoint.cases.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Note extends Model {
    @Id
    @GeneratedValue
    private UUID id;

    @NotEmpty
    private String body;

    @NotNull
    private UUID caseID;

    @NotEmpty
    @Email
    private String enteredBy;

    private String relatedActionType = "";

    private String relatedActionID = "";

    public Note() {}

    public Note(@NotEmpty String body, @NotNull UUID caseID, @NotEmpty String enteredBy) {
        this.body = body;
        this.caseID = caseID;
        this.enteredBy = enteredBy;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UUID getCaseID() {
        return caseID;
    }

    public void setCaseID(UUID caseID) {
        this.caseID = caseID;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getRelatedActionType() {
        return relatedActionType;
    }

    public void setRelatedActionType(String relatedActionType) {
        this.relatedActionType = relatedActionType;
    }

    public String getRelatedActionID() {
        return relatedActionID;
    }

    public void setRelatedActionID(String relatedActionID) {
        this.relatedActionID = relatedActionID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

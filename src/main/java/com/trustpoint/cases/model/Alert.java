package com.trustpoint.cases.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "alerts")
public class Alert extends Model {
    @Id
    @GeneratedValue
    private UUID id;

    private UUID caseID;

    @NotEmpty
    private String alertType;

    @NotNull
    private Long alertID;

    @NotEmpty
    private String description;

    @NotNull
    private Date dateCreated;

    public Alert() {}

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public Long getAlertID() {
        return alertID;
    }

    public void setAlertID(Long alertID) {
        this.alertID = alertID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public UUID getCaseID() {
        return caseID;
    }

    public void setCaseID(UUID caseID) {
        this.caseID = caseID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

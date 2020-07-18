package com.trustpoint.cases.model;

import com.trustpoint.cases.values.CaseState;
import com.trustpoint.cases.values.CaseType;
import com.trustpoint.cases.values.Priority;
import com.trustpoint.cases.values.Step;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity(name = "Case")
@Table(name = "cases")
public class Case extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Case name cannot be empty")
    private String name;

    @NotNull(message = "Case type cannot be empty")
    private CaseType type;

    @DateTimeFormat
    private Date openingDate;

    private Priority priority;

    private String description = "";

    private Step step;

    @NotEmpty(message = "Case owner cannot be empty")
    @Email()
    // case owner is not expected to be sent with request. owner will be automatically picked from
    // request subject header
    private String owner;

    private String businessUnit = "";

    private String originalBusinessUnit = "";

    private CaseState state;

    private String internalReferenceCode = "";

    private String relatedCaseID = "";

    //    @OneToMany
    //    private List<String> customers;

    // Link to notes table
    // Link to attachments table
    // Link to alerts table

    public Case() {}

    public Case(@NotBlank(message = "Case name cannot be empty") String name, @NotEmpty(message = "Case type cannot be empty") CaseType type, Date openingDate, Priority priority, String description, Step step, @NotEmpty(message = "Case owner cannot be empty") String owner, String businessUnit, String originalBusinessUnit, CaseState state, String internalReferenceCode, String relatedCaseID) {
        if (openingDate != null) {
            this.openingDate = openingDate;
        } else {
            this.openingDate = new Date();
        }

        if (state !=  null) {
            this.state = state;
        } else {
            this.state = CaseState.CREATED;
        }

        this.priority = priority;
        this.description = description;
        this.step = step;
        this.owner = owner;
        this.businessUnit = businessUnit;
        this.originalBusinessUnit = originalBusinessUnit;

        this.internalReferenceCode = internalReferenceCode;
        this.relatedCaseID = relatedCaseID;
        this.name = name;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CaseType getType() {
        return type;
    }

    public void setType(CaseType type) {
        this.type = type;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getOriginalBusinessUnit() {
        return originalBusinessUnit;
    }

    public void setOriginalBusinessUnit(String originalBusinessUnit) {
        this.originalBusinessUnit = originalBusinessUnit;
    }

    public CaseState getState() {
        return state;
    }

    public void setState(CaseState state) {
        this.state = state;
    }

    public String getInternalReferenceCode() {
        return internalReferenceCode;
    }

    public void setInternalReferenceCode(String internalReferenceCode) {
        this.internalReferenceCode = internalReferenceCode;
    }

    public String getRelatedCaseID() {
        return relatedCaseID;
    }

    public void setRelatedCaseID(String relatedCaseID) {
        this.relatedCaseID = relatedCaseID;
    }

//    public List<String> getCustomers() {
//        return customers;
//    }

//    public void setCustomers(List<String> customers) {
//        this.customers = customers;
//    }
}

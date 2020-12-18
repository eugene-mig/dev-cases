package com.trustpoint.cases.model;

import com.trustpoint.cases.dto.Alert;
import com.trustpoint.cases.values.CaseState;
import com.trustpoint.cases.values.CaseType;
import com.trustpoint.cases.values.Priority;
import com.trustpoint.cases.values.Step;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(name = "Case")
@Table(name = "cases")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Case extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private CaseType type;

    private LocalDateTime openingDate;

    private Priority priority;

    @Column(columnDefinition="text")
    private String description = "";

    private Step step;

    @Column(columnDefinition = "score")
    private int score;

    @Email()
    private String owner;

    @Column(columnDefinition = "varchar(100)", name = "business_unit")
    private String businessUnit;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(16)")
    private CaseState state;

    private String internalReferenceCode = "";

    private String relatedCaseID = "";

    @Column(columnDefinition = "TEXT")
    private String closingRemarks = "";

    private String closedBy = "";

    @DateTimeFormat
    private Date closingDate;

    @NotEmpty
    private String customer;

    @OneToMany(mappedBy = "caseID", cascade = CascadeType.ALL)
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "caseID", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "alerts")
    private List<Alert> alerts = new ArrayList<>();
}

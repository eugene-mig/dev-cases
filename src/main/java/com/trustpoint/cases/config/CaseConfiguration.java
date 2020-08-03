package com.trustpoint.cases.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseConfiguration {
    @Value("${CASE_OWNER_HAS_FULL_ACCESS:TRUE}")
    private String CaseOwnerHasFullAccess;

    @Value("${DISABLE_CASE_ACCESS_IF_UNASSIGNED:TRUE}")
    private String DisableCaseAccessIfUnassigned;

    public boolean allowAccessIfUnassigned() {
        Boolean disableAccessIfUnassigned = DisableCaseAccessIfUnassigned == "TRUE";
        return !disableAccessIfUnassigned;
    }

    public boolean caseOwnerHasAccess() {
        Boolean caseOwnerHasFullAccess = CaseOwnerHasFullAccess == "TRUE";
        return caseOwnerHasFullAccess;
    }
}

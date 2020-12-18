package com.trustpoint.cases.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class File implements Serializable {
    @JsonProperty("file_name")
    private String fileName;
    private String key;
    @JsonProperty("file_size")
    private Long fileSize;
    private String mime;
    private String bucket;
    private String url;
    @JsonProperty("last_modified")
    private Date lastModified;
}

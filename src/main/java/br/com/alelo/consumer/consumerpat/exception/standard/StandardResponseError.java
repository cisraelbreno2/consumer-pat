package br.com.alelo.consumer.consumerpat.exception.standard;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardResponseError {
    private Date timestamp;
    private String method;
    private String message;
    private Object errors;
    private String exception;
    private String status;
    private String path;
    private Boolean error;
}

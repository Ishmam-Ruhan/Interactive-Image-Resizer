package com.ishmamruhan.imageservice.ExceptionManagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorTemplate {
    private long errorCode;
    private String errorType;
    private String Details;
}

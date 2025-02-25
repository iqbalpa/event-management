package com.example.eventmanagement.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponseEntity<T> {
    private DataEntity<T> data;
    private ErrorEntity error;
    private String message;
}

package com.weatherapp.model;

import lombok.*;

@Data
@Builder
public class Error
{
    private String status;
    private String message;
}

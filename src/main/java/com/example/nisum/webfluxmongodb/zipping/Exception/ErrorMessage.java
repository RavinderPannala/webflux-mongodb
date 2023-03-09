package com.example.nisum.webfluxmongodb.zipping.Exception;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    int statusCode;
    private String errorMessage;
    private String errorDescription;

    private Date errorDate;
}

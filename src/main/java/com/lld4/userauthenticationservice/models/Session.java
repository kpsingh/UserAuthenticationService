package com.lld4.userauthenticationservice.models;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Session extends BaseModel {
    @ManyToOne
    private User user;
    private String token;

    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;

}

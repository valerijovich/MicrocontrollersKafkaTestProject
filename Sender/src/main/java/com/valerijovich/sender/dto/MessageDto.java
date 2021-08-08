package com.valerijovich.sender.dto;

import lombok.Data;

@Data
public class MessageDto {

    private int message;

    public MessageDto(int message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "message=" + message +
                '}';
    }
}

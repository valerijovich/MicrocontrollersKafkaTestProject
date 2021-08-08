package org.valerijovich.receiver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private int message;

    @Override
    public String toString() {
        return "MessageDto{" +
                "message=" + message +
                '}';
    }
}

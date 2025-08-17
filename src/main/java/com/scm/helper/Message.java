package com.scm.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Message {
    private String content;

    @Builder.Default
    private MessageType type=MessageType.blue;
}

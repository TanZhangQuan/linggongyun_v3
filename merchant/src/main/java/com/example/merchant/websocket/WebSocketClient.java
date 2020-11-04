package com.example.merchant.websocket;

import lombok.*;

import javax.websocket.Session;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketClient implements Serializable {

    private String userId;

    private Session session;

}

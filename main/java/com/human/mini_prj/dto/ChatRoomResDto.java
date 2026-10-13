package com.human.mini_prj.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.net.http.WebSocket;
import java.time.LocalDateTime;

@Getter
@Slf4j
public class ChatRoomResDto {
    private String roomId;
    private String name;
    private LocalDateTime regDate;

    @JsonIgnore //WebsocketSession 의 직렬화 방지
    private Set<WebSocketSession> sessions;
    // 세션 수가 0인지 확인 하는 메서드
    public boolean isSessionEmpty() {
        return this.sessions.size() ==0;
    }

    @Builder
    public ChatRoomResDto
}

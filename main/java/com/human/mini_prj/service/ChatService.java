package com.human.mini_prj.service;

import com.human.mini_prj.dto.ChatMessageDto;
import com.human.mini_prj.dto.ChatRoomResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final JsonMapper jsonMapper;
    private final Map<String, ChatRoomResDto> chatRooms = new ConcurrentHashMap<>();
    public List<ChatRoomResDto> findAllRoom() { return new ArrayList<>(chatRooms.values()); }  // 채팅방 목록
    public ChatRoomResDto findRoomById(String roomId) { return chatRooms.get(roomId); } // roomId 채빙방 찾기

    // 채팅방 생성
    public ChatRoomResDto createRoom(String name) {
        String roomId = UUID.randomUUID().toString();  // 유니크한 방번호 만들기
        ChatRoomResDto room = ChatRoomResDto.builder()
                .roomId(roomId).name(name).regDate(LocalDateTime.now()).build();  // 빌드 패턴으로 정보 생성
        chatRooms.put(roomId, room);  // Map으로 채팅방 목록 관리
        return room;
    }

    // 입장: 세션 등록 + 입장 메시지
    public void addSessionAndHandleEnter(String roomId, WebSocketSession session, ChatMessageDto chat) {
        ChatRoomResDto room = findRoomById(roomId);
        if (room == null) return;
        room.getSessions().add(new ConcurrentWebSocketSessionDecorator(session, 5000, 64 * 1024));
        if (chat.getSender() != null) {
            chat.setMessage(chat.getSender() + "님이 입장했습니다.");
            sendMessageToAll(roomId, chat);
        }
    }

    // 퇴장: 세션 제거 + 퇴장 메시지, 빈 방이면 삭제
    public void removeSessionAndHandleExit(String roomId, WebSocketSession session, ChatMessageDto chat) {
        ChatRoomResDto room = findRoomById(roomId);
        if (room == null) return;
        room.getSessions().removeIf(s -> s.getId().equals(session.getId()));
        if (chat.getSender() != null) {
            chat.setMessage(chat.getSender() + "님이 퇴장했습니다.");
            sendMessageToAll(roomId, chat);
        }
        if (room.isSessionEmpty()) chatRooms.remove(roomId);
    }



    // 같은 방 세션 전체에 전송
    public void sendMessageToAll(String roomId, ChatMessageDto chat) {
        ChatRoomResDto room = findRoomById(roomId);
        if (room == null) return;
        TextMessage text = new TextMessage(jsonMapper.writeValueAsString(chat));
        for (WebSocketSession s : room.getSessions()) {
            try {
                if (s.isOpen()) s.sendMessage(text);
            } catch (IOException e) {
                log.error("전송 실패: {}", e.getMessage());
            }
        }
    }



}
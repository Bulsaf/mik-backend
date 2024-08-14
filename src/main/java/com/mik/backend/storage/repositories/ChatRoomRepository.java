package com.mik.backend.storage.repositories;

import com.mik.backend.storage.entities.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, UUID> {

    Optional<ChatRoomEntity> findBySenderIdAndRecipientId(String senderId, String recipientId);

}

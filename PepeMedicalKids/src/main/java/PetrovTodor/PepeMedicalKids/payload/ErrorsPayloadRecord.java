package PetrovTodor.PepeMedicalKids.payload;

import java.time.LocalDateTime;

public record ErrorsPayloadRecord(String message, LocalDateTime timeStamp) {
}

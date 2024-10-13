package PetrovTodor.PepeMedicalKids.payload.exceptions;

import java.time.LocalDateTime;

public record ErrorsPayloadRecord(String message, LocalDateTime timeStamp) {
}

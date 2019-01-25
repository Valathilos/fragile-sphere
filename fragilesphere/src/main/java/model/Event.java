package model;

import java.time.Instant;

public class Event {
  private Instant occurredAt;
  private String detail;
  
  private Event() {
    
  }
  
  public static Event recordEvent(Instant occurredAt, String detail) {
    Event event = new Event(); 
    
    event.occurredAt = occurredAt;
    event.detail = detail;
    
    return event;
  }

  public Instant getOccurredAt() {
    return occurredAt;
  }

  public String getDetail() {
    return detail;
  }
}

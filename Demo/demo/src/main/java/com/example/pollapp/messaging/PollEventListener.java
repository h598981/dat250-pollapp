package com.example.pollapp.messaging;

import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class PollEventListener {
  private static final ConcurrentHashMap<Long, Boolean> subscribed = new ConcurrentHashMap<>();

  /** Subscribe once per pollId; on vote.cast -> invoke onVote */
  public static void subscribe(long pollId, Consumer<VoteEvent> onVote) {
    if (subscribed.putIfAbsent(pollId, true) != null) return; // already subscribed
    String ex = "poll." + pollId;
    try {
      RabbitMQService.get().ensurePollExchange(ex);
      DeliverCallback cb = (tag, delivery) -> {
        String json = new String(delivery.getBody(), StandardCharsets.UTF_8);
        if (json.contains("\"type\":\"vote.cast\"")) onVote.accept(VoteEvent.fromJson(json));
      };
      RabbitMQService.get().subscribeFanout(ex, cb);
    } catch (Exception e) { e.printStackTrace(); }
  }

  /** Minimal JSON parser (regex) for this assignment. */
  public static class VoteEvent {
    public long pollId; public long optionId; public String user; // nullable
    private static final Pattern P_LONG = Pattern.compile("\"(pollId|optionId)\"\\s*:\\s*(\\d+)");
    private static final Pattern P_USER = Pattern.compile("\"user\"\\s*:\\s*\"(.*?)\"");
    public static VoteEvent fromJson(String json){
      VoteEvent v = new VoteEvent();
      var m = P_LONG.matcher(json);
      while (m.find()) {
        if (m.group(1).equals("pollId"))   v.pollId   = Long.parseLong(m.group(2));
        if (m.group(1).equals("optionId")) v.optionId = Long.parseLong(m.group(2));
      }
      var u = P_USER.matcher(json);
      v.user = u.find() ? u.group(1) : null;
      return v;
    }
  }
}

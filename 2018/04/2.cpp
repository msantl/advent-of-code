#include <algorithm>
#include <cstdio>
#include <cstring>
#include <unordered_map>
#include <vector>
using namespace std;
const int MAX = 1000;

struct Event {
  enum class Type { SLEEP, AWAKE, GUARD };

  Type type;
  int year, month, day;
  int hour, minute;
  int guard_id;

  bool operator<(const Event &other) { return this->hash() < other.hash(); }

  long long hash() const {
    return year * 1 * 60 * 24 * 365 + month * 1 * 60 * 24 * 30 +
           day * 1 * 60 * 24 + hour * 1 * 60 + minute * 1;
  }
};

string EventType2String(const Event::Type &type) {
  switch (type) {
    case Event::Type::GUARD:
      return "GUARD";
      break;
    case Event::Type::SLEEP:
      return "SLEEP";
      break;
    case Event::Type::AWAKE:
      return "AWAKE";
      break;
  }
}

char line[MAX];
vector<Event> events;

unordered_map<int, vector<int>> sleep;
unordered_map<int, int> sleep_total;

int main(int argc, char **argv) {
  int year, month, day, hour, minute;
  int guard_id;

  while (scanf("[%d-%d-%d %d:%d]", &year, &month, &day, &hour, &minute) !=
         EOF) {
    scanf("%[^\n]\n", line);
    // printf("%s\n", line);
    if (line[1] == 'G') {
      sscanf(line, " Guard #%d begins shift", &guard_id);
      events.emplace_back(
          Event{Event::Type::GUARD, year, month, day, hour, minute, guard_id});

      sleep[guard_id].resize(60);
      for (int i = 0; i < 60; ++i) sleep[guard_id][i] = 0;
      sleep_total[guard_id] = 0;

    } else if (line[1] == 'f') {
      events.emplace_back(
          Event{Event::Type::SLEEP, year, month, day, hour, minute, -1});
    } else if (line[1] == 'w') {
      events.emplace_back(
          Event{Event::Type::AWAKE, year, month, day, hour, minute, -1});
    }
  }

  sort(events.begin(), events.end());
  auto last_event_type = Event::Type::GUARD;
  guard_id = year = month = day = hour = minute = 0;

  for (auto &ev : events) {
    /*
    printf("%d %d %d.%d.%d %d:%d %s\n", ev.guard_id, guard_id, ev.day, ev.month,
           ev.year, ev.hour, ev.minute, EventType2String(ev.type).c_str());
    */
    switch (ev.type) {
      case Event::Type::SLEEP:

        if (last_event_type == Event::Type::SLEEP) {
          sleep_total[guard_id] += 60 - minute;
          for (int i = minute; i < 60; ++i) sleep[guard_id][i]++;
        }

        year = ev.year;
        month = ev.month;
        day = ev.day;
        hour = ev.hour;
        minute = ev.minute;
        break;

      case Event::Type::AWAKE:
        if (last_event_type == Event::Type::AWAKE) {
          sleep_total[guard_id] += ev.minute;
          for (int i = 0; i < ev.minute; ++i) sleep[guard_id][i]++;
        } else if (last_event_type == Event::Type::SLEEP) {
          if (year == ev.year && month == ev.month && day == ev.day) {
            sleep_total[guard_id] += ev.minute - minute;
            for (int i = minute; i < ev.minute; ++i) sleep[guard_id][i]++;
          } else {
            sleep_total[guard_id] += 60 - minute;
            for (int i = minute; i < 60; ++i) sleep[guard_id][i]++;

            sleep_total[guard_id] += ev.minute;
            for (int i = 0; i < ev.minute; ++i) sleep[guard_id][i]++;
          }
        }
        break;

      case Event::Type::GUARD:
        if (last_event_type == Event::Type::SLEEP) {
          sleep_total[guard_id] += 60 - minute;
          for (int i = minute; i < 60; ++i) sleep[guard_id][i]++;
        }

        guard_id = ev.guard_id;
        break;
    }

    last_event_type = ev.type;
  }

  if (last_event_type == Event::Type::SLEEP) {
    sleep_total[guard_id] += 60 - minute;
    for (int i = minute; i < 60; ++i) sleep[guard_id][i]++;
  }

  int max_sleep = 0, max_sleep_id = 0, max_sleep_minute = 0;

  for (int i = 0; i < 60; ++i) {
    int curr_sleep_minute = 0;
    int curr_id = 0;
    for (auto &kv : sleep) {
      if (sleep[kv.first][i] > curr_sleep_minute) {
        curr_sleep_minute = sleep[kv.first][i];
        curr_id = kv.first;
      }
    }

    if (max_sleep < curr_sleep_minute) {
      max_sleep = curr_sleep_minute;
      max_sleep_id = curr_id;
      max_sleep_minute = i;
    }
  }

  // printf("%d %d\n", max_sleep_minute, max_sleep_id);
  printf("%d\n", max_sleep_minute * max_sleep_id);
  return 0;
}

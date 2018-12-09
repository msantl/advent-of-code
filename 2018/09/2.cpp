#include <algorithm>
#include <cstdio>
#include <cstring>
#include <list>

using namespace std;
const int MAX = 1000;

long long scores[MAX];

int main(int argc, char **argv) {
  memset(scores, 0, sizeof scores);
  int players, marbles;
  // scanf("%d%d", &players, &marbles);
  players = 458;
  marbles = 7130700;

  list<int> m;
  int player = 0;

  list<int>::iterator current = m.begin();
  current = m.insert(current, 0);

  for (int c = 1; c <= marbles; ++c) {

    if (c % 23 == 0) {
      scores[player] += c;

      auto it = current;
      for (int i = 0; i < 7; ++i) {
        if (it == m.begin()) {
          it = m.end();
        }
        it--;
      }

      scores[player] += *it;
      current = m.erase(it);

    } else {
      auto it = current;

      for (int i = 0; i < 2; ++i) {
        if (it == m.end()) {
          it = m.begin();
        }
        it++;
      }

      current = it;
      current = m.insert(current, c);
    }

    // for (auto &v : m) { printf("%d ", v); } printf("\n");

    player = (player + 1) % players;
  }

  long long best = 0;
  for (int i = 0; i < players; ++i) {
    best = max(best, scores[i]);
  }

  printf("%lld\n", best);
  return 0;
}

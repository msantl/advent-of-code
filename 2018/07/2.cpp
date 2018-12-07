#include <algorithm>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <queue>
#include <set>
#include <string>
#include <vector>

using namespace std;

const int MAXN = 250;
const int WORKERS = 5;
const int TIME = 20000;

set<char> nodes;
set<char> out[MAXN];
set<char> in[MAXN];

char worker[TIME][WORKERS];

int main(int argc, char **argv) {
  memset(worker, 0, sizeof worker);
  char a[3], b[3];
  while (scanf("Step %s must be finished before step %s can begin.\n", a, b) !=
         EOF) {
    nodes.insert(*a);
    nodes.insert(*b);

    out[*a].insert(*b);
    in[*b].insert(*a);
  }

  set<int> finished;

  for (int time = 0; time < TIME && nodes.size() > 0; ++time) {
    for (int w = 0; w < WORKERS; ++w) {
      if (time > 0 && worker[time][w] == 0 && worker[time - 1][w] != 0) {
        finished.insert(worker[time - 1][w]);
      }
    }

    for (int w = 0; w < WORKERS; ++w) {
      if (worker[time][w] == 0) {
        // take the next task if all of his dependencies have finished

        auto it = nodes.begin();

        for (; it != nodes.end(); ++it) {
          char task = *it;
          bool ok = true;
          for (auto &p : in[task]) {
            if (finished.find(p) == finished.end()) {
              ok = false;
              break;
            }
          }
          if (ok) break;
        }

        if (it != nodes.end()) {
          nodes.erase(it);
          char task = *it;
          int duration = task - 'A' + 1 + 60;
          for (int t = time; t < time + duration; ++t) {
            worker[t][w] = task;
          }
        }
      }
    }
  }

  for (int i = 0; i < TIME; ++i) {
    bool all_empty = true;
    for (int j = 0; j < WORKERS; ++j) {
      // printf("%c ", worker[i][j] == 0 ? '.' : worker[i][j]);
      if (worker[i][j] != 0) {
        all_empty = false;
         break;
      }
    }
    // printf("\n");
    if (all_empty) {
      printf("%d\n", i);
      break;
    }
  }

  return 0;
}

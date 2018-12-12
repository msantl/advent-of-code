#include <iostream>
#include <algorithm>
#include <cstdio>
#include <cstring>
#include <vector>
#include <map>

using namespace std;

const int MAXN = 100;

char pot[MAXN], rule[MAXN], result[MAXN];

map<string, char> rules;

int main(int argc, char** argv) {
  memset(pot, 0, sizeof pot);
  scanf("initial state: %s\n", pot);

  string state(pot);

  while (scanf("%s => %s\n", rule, result) != EOF) {
    rules[string(rule)] = result[0];
  }

  int off = 0;
  for (int G = 0; G < 20; ++G) {
    off += 2;
    state = ".." + state + "..";

    string next = state;

    for (int i = 2; i < state.size() - 2; ++i) {
      string now = "";
      for (int j = -2; j <= 2; ++j) {
        now += state[i + j];
      }

      if (rules.find(now) != rules.end()) {
        next[i] = rules[now];
      } else {
        next[i] = '.';
      }
    }

    // printf("%d %s\n", G + 1, state.c_str());
    state = next;
  }

  int sol = 0;
  for (int i = 0; i < state.size(); ++i) {
    if (state[i] == '#') {
      sol += i - off;
    }
  }
  printf("%d\n", sol);

  return 0;
}

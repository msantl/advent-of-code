#include <algorithm>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <map>
#include <tuple>
#include <vector>

using namespace std;

const int MAXN = 100;
const long long MAXG = 50000000000LL;

char pot[MAXN], rule[MAXN], result[MAXN];

map<string, char> rules;
map<string, pair<long long, int>> cycle;

int compact(string& s) {
  int res = 0;

  int i = 0;
  while (i < s.size() && s[i] == '.') ++i;

  if (i > 2) {
    i -= 2;
    s = s.substr(i, s.size() - i);
    res = i;
  }

  int j = 0;
  while (j < s.size() && s[s.size() - 1 - j] == '.') ++j;

  if (j > 2) {
    j -= 2;
    s = s.substr(0, s.size() - j);
  }

  return res;
}

int main(int argc, char** argv) {
  memset(pot, 0, sizeof pot);
  scanf("initial state: %s\n", pot);

  string state(pot);

  while (scanf("%s => %s\n", rule, result) != EOF) {
    rules[string(rule)] = result[0];
  }

  long long off = 0;
  for (long long G = 0; G < MAXG;) {
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

    state = next;
    off -= compact(state);
    G++;

    if (cycle.find(state) != cycle.end()) {
      long long last;
      int offset;
      tie(last, offset) = cycle[state];

      long long times = (MAXG - G) / (G - last);

      G += times * (G - last);
      off += times * (off - offset);

      cout << G << " " << off << endl;

      cycle.clear();
    } else {
      cycle[state] = make_pair(G, off);
    }
  }

  long long sol = 0;
  for (int i = 0; i < state.size(); ++i) {
    if (state[i] == '#') {
      sol += i - off;
    }
  }
  printf("%lld\n", sol);

  return 0;
}

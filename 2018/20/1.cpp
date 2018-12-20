#include <cstdio>
#include <cstring>
#include <iostream>
#include <map>
#include <queue>
#include <string>
#include <tuple>
#include <vector>
#include <set>

using namespace std;

set<tuple<int, int, string>> memo;
map<pair<int, int>, bool> doors;

int find_matching_bracket(string s) {
  int open = 0;
  for (int i = 0; i < s.size(); ++i) {
    if (s[i] == '(') open++;
    if (s[i] == ')') {
      open--;

      if (open == 0) {
        return i;
      }
    }
  }
  return -1;
}

void dfs(int x, int y, string regex) {
  auto t = make_tuple(x, y, regex);

  if (memo.find(t) != memo.end()) return;

  if (regex[0] == '$')
    return;
  else if (regex[0] == '^') {
    dfs(x, y, regex.substr(1));
    return;
  } else if (regex[0] == '(') {
    int i = find_matching_bracket(regex);

    int open = 0;
    string subregex = "";
    for (int j = 1; j < i; ++j) {
      if (regex[j] == '(')
        open++;
      else if (regex[j] == ')')
        open--;

      if (regex[j] == '|' && open == 0) {
        dfs(x, y, subregex + regex.substr(i + 1));
        subregex = "";
      } else {
        subregex += regex[j];
      }
    }

    dfs(x, y, subregex + regex.substr(i + 1));

  } else {
    if (regex[0] == 'E') {
      doors[make_pair(x, y + 1)] = true;
      dfs(x, y + 2, regex.substr(1));
    } else if (regex[0] == 'W') {
      doors[make_pair(x, y - 1)] = true;
      dfs(x, y - 2, regex.substr(1));
    } else if (regex[0] == 'N') {
      doors[make_pair(x - 1, y)] = true;
      dfs(x - 2, y, regex.substr(1));
    } else if (regex[0] == 'S') {
      doors[make_pair(x + 1, y)] = true;
      dfs(x + 2, y, regex.substr(1));
    }
  }

  memo.insert(t);
  return;
}

map<pair<int, int>, int> bio;

int dx[] = {0, 0, 1, -1};
int dy[] = {1, -1, 0, 0};

void bfs(int x, int y) {
  queue<pair<int, int>> q;
  q.push({x, y});
  bio[make_pair(x, y)] = 0;

  while (!q.empty()) {
    tie(x, y) = q.front();
    q.pop();

    for (int i = 0; i < 4; ++i) {
      int nx = x + dx[i];
      int ny = y + dy[i];

      if (doors[make_pair(nx, ny)]) {
        nx += dx[i];
        ny += dy[i];
        auto t = make_pair(nx, ny);

        if (bio[t] == 0) {
          bio[t] = bio[make_pair(x, y)] + 1;
          q.push({nx, ny});
        }
      }
    }
  }

  int sol = 0;
  for (auto& kv : bio) {
    if (kv.second > sol) {
      sol = kv.second;
    }
  }
  cout << sol << endl;
  return;
}

int main(int argc, char** argv) {
  string regex;
  cin >> regex;

  dfs(0, 0, regex);

  bfs(0, 0);

  return 0;
}

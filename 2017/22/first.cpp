#include <cstdio>
#include <cstring>
#include <iostream>
#include <map>
#include <queue>
#include <tuple>

using namespace std;

int main(int argc, char** argv) {
  char buffer[2048];
  int rows = 0, cols = 0;

  // 0 - RIGHT, 1 - LEFT
  // 2 - UP, 3 - DOWN
  int dx[] = {0, 0, -1, 1};
  int dy[] = {1, -1, 0, 0};

  map<pair<int, int>, bool> area;

  while (fgets(buffer, sizeof(buffer), stdin)) {
    cols = strlen(buffer) - 1;
    for (int j = 0; j < cols; ++j) {
      area[make_pair(rows, j)] = buffer[j] == '#' ? true : false;
    }
    rows++;
  }

  queue<tuple<int, int, int>> q;

  q.push(make_tuple(rows / 2, cols / 2, 2));

  int sol = 0;

  for (int cnt = 0; cnt < 10000 && !q.empty(); ++cnt) {
    int curr_i, curr_j, curr_k;
    tie(curr_i, curr_j, curr_k) = q.front();
    q.pop();

    int next_k;

    if (area[make_pair(curr_i, curr_j)]) {
      // infected
      // turn right
      switch (curr_k) {
        case 0:
          next_k = 3;
          break;
        case 1:
          next_k = 2;
          break;
        case 2:
          next_k = 0;
          break;
        case 3:
          next_k = 1;
          break;
      }
      // becomes clean
      area[make_pair(curr_i, curr_j)] = false;
    } else {
      // not infected
      // turn left
      switch (curr_k) {
        case 0:
          next_k = 2;
          break;
        case 1:
          next_k = 3;
          break;
        case 2:
          next_k = 1;
          break;
        case 3:
          next_k = 0;
          break;
      }
      // becomes infected
      area[make_pair(curr_i, curr_j)] = true;
      sol += 1;
    }

    // advance
    int next_i = curr_i + dx[next_k];
    int next_j = curr_j + dy[next_k];

    q.push(make_tuple(next_i, next_j, next_k));
  }

  cout << sol << endl;
  return 0;
}

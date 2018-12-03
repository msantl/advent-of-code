#include <cstdio>
#include <cstring>
#include <iostream>
#include <queue>
#include <tuple>

using namespace std;

int main(int argc, char** argv) {
  char buffer[2048];
  char area[2048][2048];
  int rows = 0, cols = 0;

  int dx[] = {0, 0, 1, -1};
  int dy[] = {1, -1, 0, 0};

  while (fgets(buffer, sizeof(buffer), stdin)) {
    cols = strlen(buffer) - 1;
    memcpy(area[rows], buffer, cols);
    rows++;
  }

  queue<tuple<int, int, int>> q;

  for (int j = 0; j < cols; ++j) {
    if (area[0][j] == '|') {
      q.push(make_tuple(0, j, 2));
      break;
    }
  }

  int sol = 0;

  while (!q.empty()) {
    int curr_i, curr_j, curr_k;
    tie(curr_i, curr_j, curr_k) = q.front();
    q.pop();

    sol++;

    int prev_i = curr_i - dx[curr_k];
    int prev_j = curr_j - dy[curr_k];

    int next_i = curr_i + dx[curr_k];
    int next_j = curr_j + dy[curr_k];
    int next_k = curr_k;

    if (area[curr_i][curr_j] == '+') {
      if (next_i < 0 || rows <= next_i || next_j < 0 || cols <= next_j ||
          area[next_i][next_j] == ' ') {
        for (int k = 0; k < 4; ++k) {
          next_i = curr_i + dx[k];
          next_j = curr_j + dy[k];
          next_k = k;

          if (next_i == prev_i && next_j == prev_j) continue;
          if (next_i < 0 || rows <= next_i) continue;
          if (next_j < 0 || cols <= next_j) continue;
          if (area[next_i][next_j] == ' ') continue;

          break;
        }
      }
    }

    if (next_i == prev_i && next_j == prev_j) continue;
    if (next_i < 0 || rows <= next_i) continue;
    if (next_j < 0 || cols <= next_j) continue;
    if (area[next_i][next_j] == ' ') continue;

    q.push(make_tuple(next_i, next_j, next_k));
  }

  cout << sol << endl;
  return 0;
}

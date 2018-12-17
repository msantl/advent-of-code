#include <algorithm>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <string>
#include <tuple>
#include <vector>
using namespace std;

const int MAX = 2000;

char grid[MAX][MAX];
char input[MAX];

int r = 0, r_min = MAX;

pair<int, int> down(int x, int y) {
  while (x <= r && grid[x][y] == 0) {
    grid[x][y] = '|';
    ++x;
  }

  return {x - 1, y};
}

void solve(int x, int y) {
  int i;
  int a, b;
  int y1, y2;
  tie(a, b) = down(x, y);
  if (a == x || a == r) return;

  bool left = false, right = false;

  if (grid[a + 1][b] == '#') {
    left = right = true;
  }

  for (int j = b - 1; j > 0; --j) {
    if (grid[a + 1][j] == 0) break;
    if (grid[a][j] == '#') {
      left = true;
      break;
    }
  }

  for (int j = b + 1; j < MAX; ++j) {
    if (grid[a + 1][j] == 0) break;
    if (grid[a][j] == '#') {
      right = true;
      break;
    }
  }

  if (!left && !right) return;

  bool overflow = 0;
  bool last_overflow = 0;

  for (i = a; i > x;) {
    if (!overflow) {
      y1 = b - 1;
      y2 = b + 1;

    } else {
      if (grid[i][y1] == '|' || grid[i][y1] == '~') y1--;
      if (grid[i][y2] == '|' || grid[i][y2] == '~') y2++;
    }

    last_overflow = overflow;

    while (y1 > 0 && grid[i][y1] == 0 &&
           (grid[i + 1][y1] == '#' || grid[i + 1][y1] == '~' ||
            grid[i + 1][y1] == '|')) {
      grid[i][y1] = '|';
      --y1;
      overflow = false;
    }

    while (y2 < MAX && grid[i][y2] == 0 &&
           (grid[i + 1][y2] == '#' || grid[i + 1][y2] == '~' ||
            grid[i + 1][y2] == '|')) {
      grid[i][y2] = '|';
      ++y2;
      overflow = false;
    }

    if (overflow) break;

    if (grid[i + 1][y1 + 1] == '#' && grid[i][y1] == 0 &&
        grid[i + 1][y1] == 0) {
      // overflow on the left
      solve(i, y1);
      overflow = true;
    }

    if (grid[i + 1][y2 - 1] == '#' && grid[i][y2] == 0 &&
        grid[i + 1][y2] == 0) {
      // overflow on the right
      solve(i, y2);
      overflow = true;
    }

    if (last_overflow && !overflow) {
      if (grid[i][y1] != '#' || grid[i][y2] != '#') break;
    }

    if (!overflow) {
      for (int j = y1 + 1; j < y2; ++j) {
        if (grid[i][j] == '|') grid[i][j] = '~';
      }
    }

    if (!overflow) --i;
  }
}

int main(int argc, char** argv) {
  memset(grid, 0, sizeof grid);

  while (scanf("%[^\n]\n", input) != EOF) {
    int x, x1, x2, y, y1, y2;

    if (sscanf(input, "x=%d, y=%d..%d", &x, &y1, &y2) == 3) {
      for (int i = y1; i <= y2; ++i) {
        grid[i][x] = '#';
      }

      r = max(r, y1);
      r = max(r, y2);
      r_min = min(r_min, y1);
      r_min = min(r_min, y2);

    } else if (sscanf(input, "y=%d, x=%d..%d", &y, &x1, &x2)) {
      for (int j = x1; j <= x2; ++j) {
        grid[y][j] = '#';
      }

      r = max(r, y);
      r_min = min(r_min, y);
    }
  }

  solve(0, 500);

  int sol = 0;
  for (int i = r_min; i < r + 1; ++i) {
    for (int j = 0; j < MAX; ++j) {
      if (grid[i][j] == '~') {
        ++sol;
      }
    }
  }

  for (int i = r_min; i < r + 1; ++i) {
    printf("%4d ", i);
    for (int j = 500 - 25; j <= 500 + 275; ++j) {
      cout << (grid[i][j] == 0 ? '.' : grid[i][j]);
    }
    cout << endl;
  }

  cout << sol << endl;
  return 0;
}

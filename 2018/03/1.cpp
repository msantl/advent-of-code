#include <cstdio>
#include <cstring>

using namespace std;

const int MAX = 1005;

int grid[MAX][MAX];

int main(int argc, char **argv) {
  memset(grid, 0, sizeof grid);

  int id, x, y, r, s;

  while (scanf("#%d @ %d,%d: %dx%d\n", &id, &x, &y, &r, &s) != EOF) {
    for (int i = x; i < x + r; ++i) {
      for (int j = y; j < y + s; ++j) {
        grid[i][j] += 1;
      }
    }
  }

  int sol = 0;
  for (int i = 0; i < MAX; ++i) {
    for (int j = 0; j < MAX; ++j) {
      if (grid[i][j] > 1) ++sol;
    }
  }

  printf("%d\n", sol);

  return 0;
}

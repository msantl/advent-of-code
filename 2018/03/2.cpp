#include <cstdio>
#include <cstring>
#include <vector>

using namespace std;

const int MAX = 1005;

int grid[MAX][MAX];

struct Rect {
  int id;
  int x, y;
  int r, s;
};

vector<Rect> rect;

int main(int argc, char **argv) {
  memset(grid, 0, sizeof grid);

  int id, x, y, r, s;

  while (scanf("#%d @ %d,%d: %dx%d\n", &id, &x, &y, &r, &s) != EOF) {
    rect.emplace_back(Rect{id, x, y, r, s});
    for (int i = x; i < x + r; ++i) {
      for (int j = y; j < y + s; ++j) {
        grid[i][j] += 1;
      }
    }
  }

  int sol = -1;

  for (auto &r : rect) {
    bool found = true;
    for (int i = r.x; i < r.x + r.r; ++i) {
      if (!found) break;
      for (int j = r.y; j < r.y + r.s; ++j) {
        if (grid[i][j] != 1) {
          found = false;
          break;
        }
      }
    }

    if (found) {
      sol = r.id;
      break;
    }
  }

  printf("%d\n", sol);
  return 0;
}

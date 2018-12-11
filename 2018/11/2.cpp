#include <algorithm>
#include <cstdio>
#include <cstring>
#include <limits>
#include <vector>

using namespace std;

const int MAXN = 305;
const int SERIAL = 8979;

int grid[MAXN][MAXN];

int main(int argc, char **argv) {
  memset(grid, 0, sizeof grid);

  for (int i = 1; i <= 300; ++i) {
    for (int j = 1; j <= 300; ++j) {
      grid[i][j] = ((((10 + i) * j + SERIAL) * (10 + i)) % 1000) / 100 - 5;
    }
  }

  int best_s;
  int best_v = numeric_limits<int>::min();
  pair<int, int> best_c;

  for (int s = 1; s <= 300; ++s) {
    for (int i = 1; i <= 300 - s; ++i) {
      for (int j = 1; j <= 300 - s; ++j) {
        int curr = 0;
        for (int k = 0; k < s; ++k) {
          for (int l = 0; l < s; ++l) {
            curr += grid[i + k][j + l];
          }
        }

        if (best_v < curr) {
          best_v = curr;
          best_c = make_pair(i, j);
          best_s = s;
        }
      }
    }
  }

  printf("%d %d %d\n", best_c.first, best_c.second, best_s);

  return 0;
}

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

  int best_v = numeric_limits<int>::min();
  pair<int, int> best_c;

  for (int i = 1; i <= 300 - 3; ++i) {
    for (int j = 1; j <= 300 - 3; ++j) {
      int curr = 0;
      for (int k = 0; k < 3; ++k) {
        for (int l = 0; l < 3; ++l) {
          curr += grid[i + k][j + l];
        }
      }

      if (best_v < curr) {
        best_v = curr;
        best_c = make_pair(i, j);
      }
    }
  }

  printf("%d %d\n", best_c.first, best_c.second);

  return 0;
}

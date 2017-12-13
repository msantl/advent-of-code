#include <cmath>
#include <cstdio>
#include <iostream>
#include <map>
#include <utility>
using namespace std;

int main(int argc, char** argv) {
  int n, sol = 0;
  cin >> n;

  map<pair<int, int>, int> grid;

  int start = 1;
  int x = 0, y = 0;
  int batch = 1;

  int dx[] = {0, 1, 0, -1};
  int dy[] = {-1, 0, 1, 0};

  int sx[] = {0, 1, 0, -1, 1, 1, -1, -1};
  int sy[] = {-1, 0, 1, 0, 1, -1, -1, 1};

  grid[make_pair(0, 0)] = 1;

  for (int i = 1; sol == 0; ++i) {
    for (int j = 0; j < batch; ++j) {
      x += dx[i % 4];
      y += dy[i % 4];

      int sum = 0;

      for (int k = 0; k < 8; ++k) {
        int x1 = x + sx[k];
        int y1 = y + sy[k];

        sum += grid[make_pair(x1, y1)];
      }

      if (sum > n) {
        sol = sum;
        break;
      }

      grid[make_pair(x, y)] = sum;
    }

    start += batch;
    if (i > 0 && i % 2 == 0) {
      batch++;
    }
  }

  cout << sol << endl;
  return 0;
}

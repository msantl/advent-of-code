#include <cmath>
#include <cstdio>
#include <iostream>
using namespace std;

int main(int argc, char** argv) {
  int n, sol = 0;
  cin >> n;

  int start = 1;
  int x = 0, y = 1;
  int batch = 1;

  int dx[] = {0, 1, 0, -1};
  int dy[] = {-1, 0, 1, 0};

  for (int i = 0;; ++i) {
    x += dx[i % 4];
    y += dy[i % 4];

    if (start <= n && n < start + batch) {
      // found the batch
      x += dx[i % 4] * (n - start);
      y += dy[i % 4] * (n - start);

      sol += abs(x) + abs(y);
      break;
    } else {
      x += dx[i % 4] * (batch - 1);
      y += dy[i % 4] * (batch - 1);
    }

    start += batch;

    if (i > 0 && i % 2 == 0) {
      batch++;
    }
  }

  cout << sol << endl;
  return 0;
}

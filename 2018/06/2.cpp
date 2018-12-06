#include <algorithm>
#include <cstdio>
#include <cstring>
#include <set>
#include <unordered_map>
#include <vector>

using namespace std;

const int MAX_DIST = 500;
const int REGION = 10000;

struct Point {
  int x, y, id;
};

int main(int argc, char **argv) {
  vector<Point> point;
  int x, y;
  int id = 0;

  int sx = std::numeric_limits<int>::max();
  int sy = std::numeric_limits<int>::max();
  int dx = std::numeric_limits<int>::min();
  int dy = std::numeric_limits<int>::min();

  while (scanf("%d, %d\n", &x, &y) != EOF) {
    point.push_back(Point{x, y, id++});

    sx = min(sx, x);
    sy = min(sy, y);
    dx = max(dx, x);
    dy = max(dy, y);
  }

  int region = 0;

  for (int i = sx - MAX_DIST; i <= dx + MAX_DIST; ++i) {
    for (int j = sy - MAX_DIST; j <= dy + MAX_DIST; ++j) {
      int dist = 0;
      for (auto &p : point) {
        dist += abs(p.x - i) + abs(p.y - j);
      }

      if (dist < REGION) region++;
    }
  }

  printf("%d\n", region);
  return 0;
}

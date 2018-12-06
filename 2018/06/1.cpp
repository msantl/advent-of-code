#include <algorithm>
#include <cstdio>
#include <cstring>
#include <set>
#include <unordered_map>
#include <vector>

using namespace std;

struct Point {
  int x, y, id;
};

unordered_map<int, int> counter;

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

  for (int i = sx; i <= dx; ++i) {
    for (int j = sy; j <= dy; ++j) {
      vector<pair<int, int>> dist;

      for (auto &p : point) {
        dist.push_back(make_pair(abs(p.x - i) + abs(p.y - j), p.id));
      }
      sort(dist.begin(), dist.end());

      if (dist.size() >= 2) {
        if (dist[0].first == dist[1].first) continue;  // same distance
      }
      counter[dist[0].second]++;
    }
  }

  int best = 0;
  for (auto &kv : counter) {
    if (kv.second > best) {
      best = kv.second;
      id = kv.first;
    }
  }
  printf("%d\n", best);
  return 0;
}

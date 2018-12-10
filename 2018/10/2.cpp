#include <algorithm>
#include <cstdio>
#include <cstring>
#include <limits>
#include <set>
#include <tuple>
#include <vector>

using namespace std;

struct Point {
  int x, y;
  int dx, dy;

  void move() {
    x += dx;
    y += dy;
  }
};

pair<int, int> normalize(vector<Point> &points) {
  int min_x = numeric_limits<int>::max();
  int min_y = numeric_limits<int>::max();
  int max_x = numeric_limits<int>::min();
  int max_y = numeric_limits<int>::min();

  for (auto &&p : points) {
    min_x = min(min_x, p.x);
    min_y = min(min_y, p.y);
  }

  for (auto &p : points) {
    p.x -= min_x;
    p.y -= min_y;

    max_x = max(max_x, p.x);
    max_y = max(max_y, p.y);
  }

  return make_pair(max_x, max_y);
}

int main(int argc, char **argv) {
  int x, y;
  int dx, dy;

  vector<Point> points;

  while (scanf("position=< %d, %d> velocity=< %d, %d>\n", &x, &y, &dx, &dy) !=
         EOF) {
    points.push_back(Point{y, x, dy, dx});
  }

  for (int k = 0; k < 100000; ++k) {
    set<pair<int, int>> has;

    int r, s;
    tie(r, s) = normalize(points);
    for (auto &p : points) {
      has.insert(make_pair(p.x, p.y));
      p.move();
    }

    if (r < 100 && s < 100) {
      for (int i = 0; i <= r; ++i) {
        for (int j = 0; j <= s; ++j) {
          if (has.find(make_pair(i, j)) != has.end())
            printf("#");
          else
            printf(".");
        }
        printf("\n");
      }
      printf("%d\n", k);
    }
  }
  return 0;
}

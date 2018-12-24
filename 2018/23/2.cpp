#include <algorithm>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <limits>
#include <map>
#include <queue>
#include <set>
#include <vector>
using namespace std;

typedef long long ll;

struct Point {
  Point(int _x, int _y, int _z) : x(_x), y(_y), z(_z) {}

  int x, y, z;

  int dist() const { return abs(x) + abs(y) + abs(z); }

  int dist(const Point &other) const {
    return abs(other.x - x) + abs(other.y - y) + abs(other.z - z);
  }

  void print() const { cout << x << " " << y << " " << z << endl; }
};

struct Nanobot : public Point {
  Nanobot(int x, int y, int z, int _r) : Point(x, y, z), r(_r) {}

  int r;

  bool canReach(const Point &other) const {
    if (dist(other) <= r) return true;
    return false;
  }

  friend bool operator<(const Nanobot &a, const Nanobot &b) {
    return a.r > b.r;
  }
};

struct Box {
  Box(Point _origin, int _length, int _bots)
      : origin(_origin), length(_length), bots(_bots) {}

  Point origin;
  int length;
  int bots;

  bool inside(const Point &p) const {
    if (p.x < origin.x || p.y < origin.y || p.z < origin.z) return false;
    if (p.x >= origin.x + length || p.y >= origin.y + length ||
        p.z >= origin.z + length)
      return false;

    return true;
  }

  bool covered(const Nanobot &n) const {
    auto range_dist = [](int x, int x1, int x2) {
      if (x < x1) return x1 - x;
      if (x > x2) return x - x2;
      return 0;
    };

    int d = range_dist(n.x, origin.x, origin.x + length - 1) +
            range_dist(n.y, origin.y, origin.y + length - 1) +
            range_dist(n.z, origin.z, origin.z + length - 1);

    if (d <= n.r) return true;
    return false;
  }

  friend bool operator<(const Box &a, const Box &b) {
    if (a.bots == b.bots) return a.origin.dist() < b.origin.dist();
    return a.bots < b.bots;
  }
};

int main(int argc, char **argv) {
  vector<Nanobot> nano;
  int x, y, z, r;

  int max_x = numeric_limits<int>::min(), min_x = numeric_limits<int>::max();
  int max_y = numeric_limits<int>::min(), min_y = numeric_limits<int>::max();
  int max_z = numeric_limits<int>::min(), min_z = numeric_limits<int>::max();

  while (scanf("pos=<%d,%d,%d>, r=%d\n", &x, &y, &z, &r) != EOF) {
    nano.push_back(Nanobot{x, y, z, r});
    max_x = max(max_x, x + r);
    min_x = min(min_x, x - r);
    max_y = max(max_y, y + r);
    min_y = min(min_y, y - r);
    max_z = max(max_z, z + r);
    min_z = min(min_z, z - r);
  }
  int n = nano.size();

  auto find_pow2 = [](int l) {
    int ret = 1;
    while (ret <= l) ret <<= 1;
    return ret;
  };

  auto max3 = [](int a, int b, int c) { return max(max(a, b), c); };

  Box box{Point(min_x, min_y, min_z),
          max3(find_pow2(max_x - min_x), find_pow2(max_y - min_y),
               find_pow2(max_z - min_z)),
          n};

  priority_queue<Box> pq;
  pq.push(box);

  while (!pq.empty()) {
    auto curr = pq.top();
    pq.pop();

    if (curr.length == 1) {
      curr.origin.print();
      cout << "Distance: " << curr.origin.dist() << endl;
      cout << "Bots: " << curr.bots << endl;
      break;
    }

    // split the box in eights
    int length = curr.length / 2;
    auto midx = Point(curr.origin.x + length, curr.origin.y, curr.origin.z);
    auto midy = Point(curr.origin.x, curr.origin.y + length, curr.origin.z);
    auto midz = Point(curr.origin.x, curr.origin.y, curr.origin.z + length);
    auto midxy =
        Point(curr.origin.x + length, curr.origin.y + length, curr.origin.z);
    auto midxz =
        Point(curr.origin.x + length, curr.origin.y, curr.origin.z + length);
    auto midyz =
        Point(curr.origin.x, curr.origin.y + length, curr.origin.z + length);
    auto midxyz = Point(curr.origin.x + length, curr.origin.y + length,
                        curr.origin.z + length);

    Box b1(curr.origin, length, 0);
    Box b2(midx, length, 0);
    Box b3(midy, length, 0);
    Box b4(midz, length, 0);
    Box b5(midxy, length, 0);
    Box b6(midxz, length, 0);
    Box b7(midyz, length, 0);
    Box b8(midxyz, length, 0);

    for (auto &n : nano) {
      if (b1.covered(n)) ++b1.bots;
      if (b2.covered(n)) ++b2.bots;
      if (b3.covered(n)) ++b3.bots;
      if (b4.covered(n)) ++b4.bots;
      if (b5.covered(n)) ++b5.bots;
      if (b6.covered(n)) ++b6.bots;
      if (b7.covered(n)) ++b7.bots;
      if (b8.covered(n)) ++b8.bots;
    }

    if (b1.bots > 0) pq.push(b1);
    if (b2.bots > 0) pq.push(b2);
    if (b3.bots > 0) pq.push(b3);
    if (b4.bots > 0) pq.push(b4);
    if (b5.bots > 0) pq.push(b5);
    if (b6.bots > 0) pq.push(b6);
    if (b7.bots > 0) pq.push(b7);
    if (b8.bots > 0) pq.push(b8);
  }

  return 0;
}

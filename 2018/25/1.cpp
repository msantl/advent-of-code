#include <algorithm>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <limits>
#include <set>
#include <vector>

using namespace std;

struct Point {
  int x, y, z, q;

  int distance(const Point &other) const {
    return abs(other.x - x) + abs(other.y - y) + abs(other.z - z) +
           abs(other.q - q);
  }
};

const int MAX = 10000;
int px[MAX];

int parent(int x) {
  if (px[x] != x) px[x] = parent(px[x]);
  return px[x];
}

int merge(int x, int y) {
  x = parent(x);
  y = parent(y);

  if (x != y) {
    px[y] = x;
  }
}

int main(int argc, char **argv) {
  memset(px, 0, sizeof px);
  vector<Point> ps;
  vector<vector<int>> cons;
  int x, y, z, q;
  while (scanf("%d,%d,%d,%d\n", &x, &y, &z, &q) != EOF) {
    ps.push_back({x, y, z, q});
  }

  for (int i = 0; i < ps.size(); ++i) px[i] = i;

  for (int i = 0; i < ps.size(); ++i) {
    for (int j = 0; j < ps.size(); ++j) {
      if (i == j) continue;
      if (ps[i].distance(ps[j]) <= 3) {
        merge(i , j);
      }
    }
  }

  set<int> count;
  for (int i = 0; i < ps.size(); ++i) count.insert(parent(i));
  cout << count.size() << endl;

  return 0;
}

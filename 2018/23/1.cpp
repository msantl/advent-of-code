#include <algorithm>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <limits>
#include <map>
#include <set>
#include <vector>
using namespace std;

struct Nanobot {
  int x, y, z;
  int r;

  bool canReach(const Nanobot &other) {
    int dist = abs(other.x - x) + abs(other.y - y) + abs(other.z - z);
    if (dist <= r) return true;
    return false;
  }

  friend bool operator<(const Nanobot &a, const Nanobot &b) {
    return a.r > b.r;
  }
};

int main(int argc, char **argv) {
  vector<Nanobot> nano;
  int x, y, z, r;
  while (scanf("pos=<%d,%d,%d>, r=%d\n", &x, &y ,&z, &r) != EOF ) {
    nano.push_back(Nanobot{x, y, z, r});
  }

  sort(nano.begin(), nano.end());

  int sol = 1;
  for (int i = 1; i < nano.size(); ++i) {
    if (nano[0].canReach(nano[i])) ++sol;
  }

  cout << sol << endl;

  return 0; }

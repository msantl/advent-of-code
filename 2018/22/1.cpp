#include <algorithm>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <set>
#include <vector>
using namespace std;

const int MAX = 3100;
const int MOD = 20183;

int geoid[MAX][MAX];

int erosion(int geo, int depth) { return (geo + depth) % MOD; }

int main(int argc, char **argv) {
  memset(geoid, 0, sizeof geoid);
  int depth, tx, ty;
  scanf("depth: %d\n", &depth);
  scanf("target: %d,%d\n", &ty, &tx);

  cout << depth << endl;
  cout << tx << " " << ty << endl;

  geoid[0][0] = 0;

  for (int j = 1; j <= ty; ++j) {
    geoid[0][j] = j * 16807;
  }

  for (int i = 1; i <= tx; ++i) {
    geoid[i][0] = i * 48271;
  }

  for (int i = 1; i <= tx; ++i) {
    for (int j = 1; j <= ty; ++j) {
      geoid[i][j] =
          erosion(geoid[i][j - 1], depth) * erosion(geoid[i - 1][j], depth);
    }
  }

  geoid[tx][ty] = 0;

  int risk = 0;

  for (int i = 0; i <= tx; ++i) {
    for (int j = 0; j <= ty; ++j) {
      int ero = erosion(geoid[i][j], depth) % 3;
      // cout << ero << " ";
      risk += ero;
    }
    // cout << endl;
  }

  cout << risk << endl;

  return 0;
}

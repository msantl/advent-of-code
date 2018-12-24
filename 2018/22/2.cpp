#include <algorithm>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <queue>
#include <set>
#include <tuple>
#include <vector>
using namespace std;

const int MAX = 5000;
const int MOD = 20183;

int geoid[MAX][MAX];
int ero[MAX][MAX];

int erosion(int geo, int depth) { return (geo + depth) % MOD; }

const int NONE = 0;
const int TORCH = 1;
const int CLIMB = 2;
int dist[MAX][MAX][3];

int dx[] = {0, 0, -1, 1};
int dy[] = {-1, 1, 0, 0};

class Compare {
 public:
  bool operator()(const tuple<int, int, int, int> &lhs,
                  const tuple<int, int, int, int> &rhs) const {
    return get<3>(lhs) > get<3>(rhs);
  }
};

int bfs(int x, int y) {
  priority_queue<tuple<int, int, int, int>, vector<tuple<int, int, int, int>>,
                 Compare>
      q;
  memset(dist, 1, sizeof dist);
  dist[0][0][TORCH] = 0;
  q.push(make_tuple(0, 0, TORCH, 0));

  while (!q.empty()) {
    int cx, cy, ce, cd;
    tie(cx, cy, ce, cd) = q.top();
    q.pop();

    if (cx == x && cy == y && ce == TORCH) break;

    for (int i = 0; i < 4; ++i) {
      int nx = cx + dx[i];
      int ny = cy + dy[i];
      int d = cd + 1;

      if (nx < 0 || ny < 0 || nx >= MAX || ny >= MAX) continue;

      auto add_in_queue = [&q](int nx, int ny, int ne, int d) {
        if (d < dist[nx][ny][ne]) {
          dist[nx][ny][ne] = d;
          q.push(make_tuple(nx, ny, ne, d));
        }
      };

      if (ero[nx][ny] == 0) {  // rocky
        if (ce == CLIMB) {
          add_in_queue(nx, ny, CLIMB, d);
          add_in_queue(nx, ny, TORCH, d + 7);
        } else if (ce == TORCH) {
          add_in_queue(nx, ny, CLIMB, d + 7);
          add_in_queue(nx, ny, TORCH, d);
        } else if (ce == NONE) {
          if (ero[cx][cy] == 0) {
            cout << "SOMETHING WENT WRONG: rocky with none" << endl;
          } else if (ero[cx][cy] == 1) {
            add_in_queue(nx, ny, CLIMB, d + 7);
          } else if (ero[cx][cy] == 2) {
            add_in_queue(nx, ny, TORCH, d + 7);
          }
        }
      } else if (ero[nx][ny] == 1) {  // wet
        if (ce == CLIMB) {
          add_in_queue(nx, ny, CLIMB, d);
          add_in_queue(nx, ny, NONE, d + 7);
        } else if (ce == NONE) {
          add_in_queue(nx, ny, CLIMB, d + 7);
          add_in_queue(nx, ny, NONE, d);
        } else if (ce == TORCH) {
          if (ero[cx][cy] == 0) {
            add_in_queue(nx, ny, CLIMB, d + 7);
          } else if (ero[cx][cy] == 1) {
            cout << "SOMETHING WENT WRONG: wet with torch" << endl;
          } else if (ero[cx][cy] == 2) {
            add_in_queue(nx, ny, NONE, d + 7);
          }
        }
      } else if (ero[nx][ny] == 2) {  // narrow
        if (ce == NONE) {
          add_in_queue(nx, ny, TORCH, d + 7);
          add_in_queue(nx, ny, NONE, d);
        } else if (ce == TORCH) {
          add_in_queue(nx, ny, TORCH, d);
          add_in_queue(nx, ny, NONE, d + 7);
        } else if (ce == CLIMB) {
          if (ero[cx][cy] == 0) {
            add_in_queue(nx, ny, TORCH, d + 7);
          } else if (ero[cx][cy] == 1) {
            add_in_queue(nx, ny, NONE, d + 7);
          } else if (ero[cx][cy] == 2) {
            cout << "SOMETHING WENT WRONG: narrow with climb" << endl;
          }
        }
      }
    }
  }

  return dist[x][y][TORCH];
}

int main(int argc, char **argv) {
  memset(ero, 0, sizeof ero);
  memset(geoid, 0, sizeof geoid);
  int depth, tx, ty;
  scanf("depth: %d\n", &depth);
  scanf("target: %d,%d\n", &ty, &tx);

  geoid[0][0] = 0;

  for (int j = 1; j < MAX; ++j) {
    geoid[0][j] = j * 16807;
  }

  for (int i = 1; i < MAX; ++i) {
    geoid[i][0] = i * 48271;
  }

  for (int i = 1; i < MAX; ++i) {
    for (int j = 1; j < MAX; ++j) {
      geoid[i][j] =
          erosion(geoid[i][j - 1], depth) * erosion(geoid[i - 1][j], depth);
    }
  }

  geoid[tx][ty] = 0;

  for (int i = 0; i < MAX; ++i) {
    for (int j = 0; j < MAX; ++j) {
      ero[i][j] = erosion(geoid[i][j], depth) % 3;
    }
  }

  cout << bfs(tx, ty) << endl;

  return 0;
}

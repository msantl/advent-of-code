#include <algorithm>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <limits>
#include <queue>
#include <set>
#include <string>
#include <tuple>
#include <vector>

using namespace std;

int r = 0, s = 0;
char grid[100][100], GRID[100][100];
int ids[100][100], IDS[100][100];

struct Unit {
  int id;
  int x, y;
  char t;
  int hp = 200;
  bool moved = false;
};

vector<Unit> unit, UNIT;
int dist[100][100];
int dx[] = {-1, 0, 0, 1};
int dy[] = {0, -1, 1, 0};

int enemy_in_range(int x, int y, char t) {
  set<pair<int, int>> enemy;
  for (int i = 0; i < 4; ++i) {
    int nx = x + dx[i];
    int ny = y + dy[i];
    if (grid[nx][ny] == '#') continue;
    if (grid[nx][ny] == '.') continue;

    if (grid[nx][ny] != t && unit[ids[nx][ny]].hp > 0) {
      enemy.insert({unit[ids[nx][ny]].hp, i});
    }
  }
  if (enemy.size() > 0) {
    int id = enemy.begin()->second;
    return ids[x + dx[id]][y + dy[id]];
  }
  return -1;
}

int backtrace[100][100];

int flood_fill(int x, int y, char t) {
  memset(backtrace, -1, sizeof backtrace);
  memset(dist, 1, sizeof dist);

  // cout << "Start " << x << " " << y << " " << t << endl;
  queue<pair<int, int>> q;
  q.push({x, y});
  dist[x][y] = 0;

  vector<pair<int, int>> res;

  while (!q.empty()) {
    tie(x, y) = q.front();
    q.pop();

    for (int i = 0; i < 4; ++i) {
      int nx = x + dx[i];
      int ny = y + dy[i];
      if (dist[nx][ny] <= dist[x][y] + 1) continue;
      if (grid[nx][ny] == '#') continue;

      if (grid[nx][ny] == '.') {
        dist[nx][ny] = dist[x][y] + 1;
        int opt_id = enemy_in_range(nx, ny, t);
        if (opt_id != -1) {
          res.push_back({nx, ny});
          backtrace[nx][ny] = i;
        } else {
          q.push({nx, ny});
          backtrace[nx][ny] = i;
        }
      }
    }
  }

  sort(res.begin(), res.end());
  if (res.size() > 0) {
    int min_id = 0, min_dist = dist[res[0].first][res[0].second];
    for (int i = 1; i < res.size(); ++i) {
      if (dist[res[i].first][res[i].second] < min_dist) {
        min_dist = dist[res[i].first][res[i].second];
        min_id = i;
      }
    }
    x = res[min_id].first;
    y = res[min_id].second;
    int b = -1;
    // cout << "End " << x << " " << y << endl;
    while (backtrace[x][y] != -1) {
      b = backtrace[x][y];
      x -= dx[b];
      y -= dy[b];
    }

    return b;
  }
  return -1;
}

int simulate(int elf_power) {
  unit = UNIT;
  for (int i = 0; i < r; ++i) {
    for (int j = 0; j < s; ++j) {
      grid[i][j] = GRID[i][j];
      ids[i][j] = IDS[i][j];
    }
  }

  int round;
  bool finished = false;
  for (round = 0; !finished; ++round) {
    for (auto &u : unit) {
      u.moved = false;
    }

    bool action = false;
    for (int i = 0; i < r && !finished; ++i) {
      for (int j = 0; j < s && !finished; ++j) {
        if (ids[i][j] == -1) continue;
        auto &u = unit[ids[i][j]];
        if (u.hp <= 0 || u.moved) continue;

        int opt_id = enemy_in_range(i, j, grid[i][j]);

        int attack = 3;
        if (u.t == 'E') attack = elf_power;

        if (opt_id != -1) {
          unit[opt_id].hp -= attack;
          action = true;
          if (unit[opt_id].hp <= 0) {
            if (u.t == 'G') return -1;
            int ex = unit[opt_id].x;
            int ey = unit[opt_id].y;
            grid[ex][ey] = '.';
            ids[ex][ey] = -1;
          }
        } else {
          int res = flood_fill(i, j, grid[i][j]);
          if (res != -1) {
            grid[u.x][u.y] = '.';
            ids[u.x][u.y] = -1;
            u.x += dx[res];
            u.y += dy[res];
            u.moved = true;
            grid[u.x][u.y] = u.t;
            ids[u.x][u.y] = u.id;
            action = true;
          }

          opt_id = enemy_in_range(u.x, u.y, u.t);
          if (opt_id != -1) {
            unit[opt_id].hp -= attack;
            action = true;
            if (unit[opt_id].hp <= 0) {
              if (u.t == 'G') return -1;
              int ex = unit[opt_id].x;
              int ey = unit[opt_id].y;
              grid[ex][ey] = '.';
              ids[ex][ey] = -1;
            }
          }
        }
      }
    }

    if (!action) {
      round -= 1;
      finished = false;
      break;
    }
  }

  int sum = 0;
  for (auto &u : unit) {
    if (u.hp > 0) sum += u.hp;
  }

  return sum * round;
}

int main(int argc, char **argv) {
  memset(GRID, 0, sizeof GRID);
  memset(IDS, -1, sizeof IDS);
  for (; scanf("%s\n", GRID[r]) != EOF; ++r) {
    s = max(s, (int)strlen(GRID[r]));
  }

  int id = 0;
  for (int i = 0; i < r; ++i) {
    for (int j = 0; j < s; ++j) {
      if (GRID[i][j] == 'E' || GRID[i][j] == 'G') {
        IDS[i][j] = id;
        UNIT.push_back(Unit{id++, i, j, GRID[i][j]});
      }
    }
  }

  for (int i = 4;; ++i) {
    int res = simulate(i);
    if (res != -1) {
      cout << res << endl;
      break;
    }
  }

  return 0;
}

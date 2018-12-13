#include <algorithm>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <set>
#include <string>
#include <tuple>
#include <vector>

using namespace std;

const int MAX = 200;

char grid[MAX][MAX];

enum class Direction { L, R, U, D };

pair<int, int> DirectionToVector(const Direction &d) {
  if (d == Direction::L) {
    return {0, -1};
  } else if (d == Direction::R) {
    return {0, 1};
  } else if (d == Direction::U) {
    return {-1, 0};
  } else if (d == Direction::D) {
    return {1, 0};
  }
}

string DirectionToString(const Direction &d) {
  if (d == Direction::L) {
    return "LEFT";
  } else if (d == Direction::R) {
    return "RIGHT";
  } else if (d == Direction::U) {
    return "UP";
  } else if (d == Direction::D) {
    return "DOWN";
  }
}

struct Cart {
  int x, y;
  Direction d;

  int intersection = 0;

  friend bool operator<(const Cart &lhs, const Cart &rhs) {
    if (lhs.x == rhs.x) return lhs.y < rhs.y;
    return lhs.x < rhs.x;
  }
};

int main(int argc, char **argv) {
  memset(grid, 0, sizeof grid);
  int r, s = 0;
  for (r = 0; scanf("%[^\n]\n", grid[r]) != EOF; ++r) {
    s = max(s, (int)(strlen(grid[r])));
  }

  vector<Cart> cart;

  for (int i = 0; i < r; ++i) {
    for (int j = 0; j < s; ++j) {
      switch (grid[i][j]) {
        case '>':
          grid[i][j] = '-';
          cart.push_back(Cart{i, j, Direction::R});
          break;
        case '<':
          grid[i][j] = '-';
          cart.push_back(Cart{i, j, Direction::L});
          break;
        case 'v':
          grid[i][j] = '|';
          cart.push_back(Cart{i, j, Direction::D});
          break;
        case '^':
          grid[i][j] = '|';
          cart.push_back(Cart{i, j, Direction::U});
          break;
        default:
          // ignore
          break;
      }
    }
  }

  bool crash = false;
  int X, Y;
  while (!crash) {
    sort(cart.begin(), cart.end());

    set<pair<int, int>> pos;

    for (auto &c : cart) {
      pos.insert({c.x, c.y});
    }

    for (auto &c : cart) {
      pos.erase(pos.find({c.x, c.y}));

      int dx, dy;
      tie(dx, dy) = DirectionToVector(c.d);

      c.x += dx;
      c.y += dy;

      // collision check
      if (pos.find({c.x, c.y}) != pos.end()) {
        crash = true;
        X = c.x;
        Y = c.y;
        break;
      } else {
        pos.insert({c.x, c.y});
      }

      switch (grid[c.x][c.y]) {
        case '|':
          // ignore
          break;
        case '-':
          // ignore
          break;
        case '\\':
          if (c.d == Direction::L) {
            c.d = Direction::U;
          } else if (c.d == Direction::R) {
            c.d = Direction::D;
          } else if (c.d == Direction::D) {
            c.d = Direction::R;
          } else if (c.d == Direction::U) {
            c.d = Direction::L;
          }

          break;
        case '/':
          if (c.d == Direction::L) {
            c.d = Direction::D;
          } else if (c.d == Direction::R) {
            c.d = Direction::U;
          } else if (c.d == Direction::D) {
            c.d = Direction::L;
          } else if (c.d == Direction::U) {
            c.d = Direction::R;
          }

          break;
        case '+':
          if (c.intersection == 0) {
            // left

            if (c.d == Direction::L) {
              c.d = Direction::D;
            } else if (c.d == Direction::R) {
              c.d = Direction::U;
            } else if (c.d == Direction::U) {
              c.d = Direction::L;
            } else if (c.d == Direction::D) {
              c.d = Direction::R;
            }

          } else if (c.intersection == 1) {
            // straight
          } else if (c.intersection == 2) {
            // right

            if (c.d == Direction::L) {
              c.d = Direction::U;
            } else if (c.d == Direction::R) {
              c.d = Direction::D;
            } else if (c.d == Direction::D) {
              c.d = Direction::L;
            } else if (c.d == Direction::U) {
              c.d = Direction::R;
            }
          }
          c.intersection = (c.intersection + 1) % 3;
          break;
        default:
          break;
      }
    }
  }

  printf("%d,%d\n", Y, X);
  return 0;
}

#include <cstdio>
#include <cstring>
#include <iostream>
#include <tuple>
#include <vector>

using namespace std;

int bio[128];
vector<pair<int, int>> connectors;

int dfs(int p) {
  int strength = 0;
  int c1, c2;

  for (int x = 0; x < connectors.size(); ++x) {
    if (bio[x]) continue;
    tie(c1, c2) = connectors[x];

    if (p == c1) {
      bio[x] = true;
      strength = max(c1 + c2 + dfs(c2), strength);
      bio[x] = false;
    } else if (p == c2) {
      bio[x] = true;
      strength = max(c1 + c2 + dfs(c1), strength);
      bio[x] = false;
    }
  }

  return strength;
}

int main(int argc, char** argv) {
  char buffer[2048];
  while (fgets(buffer, sizeof(buffer), stdin)) {
    int p1, p2;
    sscanf(buffer, "%d/%d", &p1, &p2);
    connectors.push_back(make_pair(p1, p2));
  }

  memset(bio, 0, sizeof(bio));
  int sol = dfs(0);

  cout << sol << endl;

  return 0;
}

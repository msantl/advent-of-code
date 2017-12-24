#include <cstdio>
#include <cstring>
#include <iostream>
#include <tuple>
#include <vector>

using namespace std;

int bio[128];
vector<pair<int, int>> connectors;

int length, strength;

void dfs(int p, int len, int str) {
  int c1, c2;
  if (len > length || (len == length && str > strength)) {
    strength = str;
    length = len;
  }

  for (int x = 0; x < connectors.size(); ++x) {
    if (bio[x]) continue;
    tie(c1, c2) = connectors[x];

    if (p == c1) {
      bio[x] = true;
      dfs(c2, len + 1, str + c1 + c2);
      bio[x] = false;
    } else if (p == c2) {
      bio[x] = true;
      dfs(c1, len + 1, str + c1 + c2);
      bio[x] = false;
    }
  }
}

int main(int argc, char** argv) {
  char buffer[2048];
  while (fgets(buffer, sizeof(buffer), stdin)) {
    int p1, p2;
    sscanf(buffer, "%d/%d", &p1, &p2);
    connectors.push_back(make_pair(p1, p2));
  }

  memset(bio, 0, sizeof(bio));
  strength = 0, length = 0;

  dfs(0, 0, 0);
  cout << strength << " " << length << endl;

  return 0;
}

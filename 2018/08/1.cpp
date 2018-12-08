#include <cstdio>
#include <algorithm>
#include <cstring>
#include <string>
#include <vector>

using namespace std;

vector<int> tree;

int dfs(int i, int &sum) {
  int child = tree[i];
  int meta = tree[i + 1];

  i += 2;
  for (int j = 0; j < child; ++j) {
    i = dfs(i, sum);
  }

  for (int j = 0; j < meta; ++j) {
    sum += tree[i + j];
  }
  return i + meta;
}

int main(int argc, char **argv) {
  int d;

  while (scanf("%d", &d) != EOF) {
    tree.push_back(d);
  }

  int sol = 0;
  dfs(0, sol);
  printf("%d\n", sol);
  return 0;
}

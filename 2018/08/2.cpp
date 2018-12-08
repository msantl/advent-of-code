#include <algorithm>
#include <cstdio>
#include <cstring>
#include <string>
#include <vector>
#include <tuple>

using namespace std;

vector<int> tree;

pair<int, int> dfs(int i) {
  int child = tree[i];
  int meta = tree[i + 1];
  vector<int> children;
  children.resize(child);

  i += 2;
  for (int j = 0; j < child; ++j) {
    int weight;
    tie(i, weight) = dfs(i);
    children[j] = weight;
  }

  int sum = 0;
  if (child == 0) {
    for (int j = 0; j < meta; ++j) {
      sum += tree[i + j];
    }
  } else {
    for (int j = 0; j < meta; ++j) {
      int x = tree[i + j];
      if (x < 1 || x > child ) {
        sum += 0;
      } else {
        sum += children[x - 1];
      }
    }
  }
  return make_pair(i + meta, sum);
}

int main(int argc, char **argv) {
  int d;

  while (scanf("%d", &d) != EOF) {
    tree.push_back(d);
  }

  int sol = 0;
  tie(ignore, sol) = dfs(0);
  printf("%d\n", sol);
  return 0;
}

#include <algorithm>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <queue>
#include <set>
#include <string>
#include <vector>

using namespace std;

const int MAXN = 250;

set<char> nodes;
set<char> node[MAXN];
int in_degree[MAXN];
int out_degree[MAXN];
int bio[MAXN];

class cmp {
 public:
  bool operator()(char a, char b) { return a > b; }
};

int main(int argc, char **argv) {
  memset(in_degree, 0, sizeof in_degree);
  memset(bio, 0, sizeof bio);

  char a[3], b[3];
  while (scanf("Step %s must be finished before step %s can begin.\n", a, b) !=
         EOF) {
    nodes.insert(*a);
    nodes.insert(*b);
    node[*a].insert(*b);
    out_degree[*a]++;
    in_degree[*b]++;
  }

  priority_queue<char, vector<char>, cmp> pq;

  for (auto &n : nodes) {
    if (in_degree[n] == 0) {
      pq.push(n);
    }
  }

  string sol = "";

  while (!pq.empty()) {
    auto curr = pq.top();
    pq.pop();

    sol += curr;

    for (auto &n : node[curr]) {
      in_degree[n] --;
      if (in_degree[n] == 0)
        pq.push(n);
    }
  }
  cout << sol << endl;
  return 0;
}

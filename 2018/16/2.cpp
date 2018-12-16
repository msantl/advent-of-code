#include <algorithm>
#include <cstdio>
#include <cstring>
#include <functional>
#include <iostream>
#include <map>
#include <set>
#include <string>
#include <vector>

using namespace std;
int input[4];
int r[4], e[4];

void addr(int a, int b, int c) { r[c] = r[a] + r[b]; }

void addi(int a, int b, int c) { r[c] = r[a] + b; }

void mulr(int a, int b, int c) { r[c] = r[a] * r[b]; }

void muli(int a, int b, int c) { r[c] = r[a] * b; }

void banr(int a, int b, int c) { r[c] = r[a] & r[b]; }

void bani(int a, int b, int c) { r[c] = r[a] & b; }

void borr(int a, int b, int c) { r[c] = r[a] | r[b]; }

void bori(int a, int b, int c) { r[c] = r[a] | b; }

void setr(int a, int b, int c) { r[c] = r[a]; }

void seti(int a, int b, int c) { r[c] = a; }

void gtir(int a, int b, int c) { r[c] = a > r[b]; }

void gtri(int a, int b, int c) { r[c] = r[a] > b; }

void gtrr(int a, int b, int c) { r[c] = r[a] > r[b]; }

void eqir(int a, int b, int c) { r[c] = a == r[b]; }

void eqri(int a, int b, int c) { r[c] = r[a] == b; }

void eqrr(int a, int b, int c) { r[c] = r[a] == r[b]; }

int main(int argc, char **argv) {
  vector<function<void(int, int, int)>> ops = {
      eqrr, eqri, eqir, gtrr, gtri, gtir, seti, setr,
      bori, borr, bani, banr, muli, mulr, addi, addr};

  map<int, set<int>> mapping;

  int sol = 0;
  int op, a, b, c;
  while (scanf("Before: [%d, %d, %d, %d]\n", &input[0], &input[1], &input[2],
               &input[3]) == 4) {
    scanf("%d %d %d %d\n", &op, &a, &b, &c);
    scanf("After: [%d, %d, %d, %d]\n", &e[0], &e[1], &e[2], &e[3]);

    int count = 0;
    for (int k = 0; k < ops.size(); ++k) {
      auto &f = ops[k];

      for (int i = 0; i < 4; ++i) {
        r[i] = input[i];
      }

      f(a, b, c);

      bool ok = true;
      for (int i = 0; i < 4; ++i) {
        if (r[i] != e[i]) {
          ok = false;
        }
      }

      if (ok) {
        mapping[op].insert(k);
      }
    }
  }

  map<int, int> op_to_fun;

  while (true) {
    vector<int> to_delete;
    for (auto &kv : mapping) {
      auto &s = kv.second;
      if (s.size() == 1) {
        int fun = *(s.begin());
        op_to_fun[kv.first] = fun;
        to_delete.push_back(fun);
      }
    }

    if (to_delete.size() == 0) break;

    for  (auto &v : to_delete) {
      for (auto &kv : mapping) {
        auto it = kv.second.find(v);
        if  (it != kv.second.end()) {
          kv.second.erase(it);
        }
      }
    }
  }

  memset(r, 0, sizeof r);
  while (scanf("%d %d %d %d\n", &op, &a, &b, &c) != EOF) {
    ops[op_to_fun[op]] (a, b, c);
  }
  cout << r[0] << endl;
  return 0;
}

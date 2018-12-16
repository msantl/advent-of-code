#include <algorithm>
#include <cstdio>
#include <cstring>
#include <functional>
#include <iostream>
#include <map>
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
  vector<function<void(int, int, int)> > ops = {
      eqrr, eqri, eqir, gtrr, gtri, gtir, seti, setr,
      bori, borr, bani, banr, muli, mulr, addi, addr};

  int sol = 0;
  int op, a, b, c;
  while (scanf("Before: [%d, %d, %d, %d]\n", &input[0], &input[1], &input[2],
               &input[3]) != EOF) {
    scanf("%d %d %d %d\n", &op, &a, &b, &c);
    scanf("After: [%d, %d, %d, %d]\n", &e[0], &e[1], &e[2], &e[3]);

    int count = 0;
    for (auto &f : ops) {
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
        ++count;
      }
    }

    if (count >= 3) ++sol;
  }

  cout << sol << endl;
  return 0;
}

#include <algorithm>
#include <cstdio>
#include <cstring>
#include <functional>
#include <iostream>
#include <map>
#include <set>
#include <string>
#include <unordered_map>
#include <vector>

using namespace std;
int r[6];

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

unordered_map<string, function<void(int, int, int)>> fun = {
    {"addr", addr}, {"addi", addi}, {"mulr", mulr}, {"muli", muli},
    {"banr", banr}, {"bani", bani}, {"borr", borr}, {"bori", bori},
    {"setr", setr}, {"seti", seti}, {"gtri", gtri}, {"gtrr", gtrr},
    {"eqir", eqir}, {"eqri", eqri}, {"eqrr", eqrr}, {"gtir", gtir}};

struct Cmd {
  function<void(int, int, int)> fun;
  int a, b, c;

  void execute() { fun(a, b, c); }
};

vector<Cmd> cmds;

int main(int argc, char **argv) {
  memset(r, 0, sizeof r);

  char cmd[5];
  int a, b, c, ip_id;
  scanf("#ip %d\n", &ip_id);
  while (scanf("%s %d %d %d\n", cmd, &a, &b, &c) != EOF) {
    cmds.push_back(Cmd{fun[cmd], a, b, c});
  }

  int last_r2;
  // r[0] = 6267260;
  set<int> s;
  while (true) {
    cmds[r[ip_id]].execute();
    if (r[ip_id] + 1 >= cmds.size()) break;
    r[ip_id]++;

    if (r[ip_id] == 28) {
      if (s.find(r[2]) != s.end()) break;
      last_r2 = r[2];
      s.insert(r[2]);
    }
  }
  cout << last_r2 << endl;
  return 0;
}

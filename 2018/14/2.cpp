#include <algorithm>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <vector>
using namespace std;

vector<int> recipe = {3, 7};
const int INPUT = 768071;

int get_last(int n, const vector<int> &v) {
  int x = 0;
  if (v.size() < n) return -1;

  for (int i = n - 1; i >= 0; --i) {
    x = x * 10 + v[v.size() - 1 - i];
  }

  return x;
}

int main(int argc, char **argv) {
  int elf1 = 0, elf2 = 1;
  bool found = false;

  while (!found) {
    int score = recipe[elf1] + recipe[elf2];

    if (score > 9) {
      recipe.push_back(1);
      if (get_last(6, recipe) == INPUT) {
        cout << recipe.size() - 6 << endl;
        found = true;
      }
    }

    recipe.push_back(score % 10);
    if (get_last(6, recipe) == INPUT) {
      cout << recipe.size() - 6 << endl;
      found = true;
    }

    elf1 = (elf1 + 1 + recipe[elf1]) % recipe.size();
    elf2 = (elf2 + 1 + recipe[elf2]) % recipe.size();
  }

  return 0;
}

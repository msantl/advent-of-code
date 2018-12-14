#include <algorithm>
#include <iostream>
#include <cstdio>
#include <cstring>
#include <vector>
using namespace std;

vector<int> recipe = {3, 7};

int main(int argc, char **argv) {
  int elf1 = 0, elf2 = 1;

  for (; recipe.size() < 768071 + 10; ) {
    int score = recipe[elf1] + recipe[elf2];

    if (score > 9) {
      recipe.push_back(1);
    }
    recipe.push_back(score % 10);

    elf1 = (elf1 + 1 + recipe[elf1]) % recipe.size();
    elf2 = (elf2 + 1 + recipe[elf2]) % recipe.size();
  }

  for (int i = 9; i >= 0; i--) {
    printf("%d", recipe[recipe.size() - 1 - i]);
  }
  printf("\n");

  return 0;
}

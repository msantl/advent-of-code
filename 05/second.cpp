#include <cstdio>
#include <iostream>
#include <vector>
using namespace std;

int main(int argc, char **argv) {
  char buffer[128];
  vector<int> instructions;
  while (fgets(buffer, sizeof(buffer), stdin)) {
    int jmp;
    sscanf(buffer, "%d", &jmp);

    instructions.push_back(jmp);
  }

  int ptr = 0, cnt = 0;

  while (1) {
    int &jmp = instructions[ptr];

    ptr += jmp;
    cnt++;

    if (ptr < 0 || ptr >= instructions.size()) {
      break;
    }

    if (jmp >= 3) {
      jmp -= 1;
    } else {
      jmp += 1;
    }
  }

  cout << cnt << endl;
  return 0;
}

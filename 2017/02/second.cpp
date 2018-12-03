#include <algorithm>
#include <climits>
#include <cstdlib>
#include <iostream>
#include <vector>
using namespace std;
int main(int argc, char **argv) {
  char buffer[2048];
  int checksum = 0;

  while (fgets(buffer, sizeof buffer, stdin)) {
    int curr = 0, sign = 1;
    vector<int> numbers;
    for (char *c = buffer; *c; ++c) {
      if ('0' <= *c && *c <= '9') {
        curr = curr * 10 + (*c - '0');
      } else if (*c == '-') {
        sign = -1;
      } else {
        curr = sign * curr;
        numbers.push_back(curr);
        curr = 0;
        sign = 1;
      }
    }

    int b = 0, n = 1;
    for (int i = 0; i < numbers.size(); ++i) {
      for (int j = 0; j < numbers.size(); ++j) {
        if (i == j) continue;
        if (numbers.at(i) % numbers.at(j) == 0) {
          b = numbers.at(i);
          n = numbers.at(j);
        }
      }
    }

    checksum += b / n;
  }

  cout << checksum << endl;

  return 0;
}

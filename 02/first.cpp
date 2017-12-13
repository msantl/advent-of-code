#include <algorithm>
#include <climits>
#include <cstdlib>
#include <iostream>
using namespace std;
int main(int argc, char **argv) {
  char buffer[2048];
  int checksum = 0;

  while (fgets(buffer, sizeof buffer, stdin)) {
    int curr = 0, sign = 1, maxi = INT_MIN, mini = INT_MAX;
    for (char *c = buffer; *c; ++c) {
      if ('0' <= *c && *c <= '9') {
        curr = curr * 10 + (*c - '0');
      } else if (*c == '-') {
        sign = -1;
      } else {
        curr = sign * curr;
        mini = min(mini, curr);
        maxi = max(maxi, curr);
        curr = 0;
        sign = 1;
      }
    }

    checksum += maxi - mini;
  }

  cout << checksum << endl;

  return 0;
}
